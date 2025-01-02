package com.example.quizapp.ui.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.WholeQuestion
import com.example.quizapp.data.models.WholeQuiz
import com.example.quizapp.data.repositories.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditQuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val questionId: Int = savedStateHandle.get<String>("questionId")?.toInt() ?: -1

    private val _quiz = MutableStateFlow<WholeQuiz?>(null)
    val quiz: StateFlow<WholeQuiz?> = _quiz

    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> = _title

    private val _questionText = MutableLiveData<String>()
    val questionText: LiveData<String> = _questionText

    private val _question = MutableLiveData<WholeQuestion>()
    val question: LiveData<WholeQuestion> = _question

    private val _currentQuestionAnswer = MutableStateFlow<Int?>(null)
    val currentQuestionAnswer: StateFlow<Int?> = _currentQuestionAnswer

    val quizId: Int = savedStateHandle.get<String>("quizId")?.toInt() ?: -1

    private val _navigationEvents = MutableSharedFlow<String?>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val _questionList = MutableStateFlow<List<QuestionEntity>>(listOf())
    val questionList: StateFlow<List<QuestionEntity>> = _questionList

    init {
        viewModelScope.launch {
            _questionList.value = quizRepository.getQuestionsByQuizId(quizId)
            _navigationEvents.emit(null)
            _quiz.value = quizRepository.getWholeQuiz(quizId)
            if (quiz.value == null) {
                _quiz.value = quizRepository.getQuizByQuestionId(questionId)
            }
            _currentQuestionAnswer.value =
                quiz.value?.questions?.find { it.question.uid == questionId }?.answerOptions?.find { it.correct }?.uid
            _question.value = quiz.value?.questions?.find { it.question.uid == questionId }
            _title.value = quiz.value?.quiz?.title.toString()
        }
    }

    fun navigateToEditQuestion(questionId: Int) {
        viewModelScope.launch {
            _navigationEvents.emit("editQuestion/$questionId")
        }
    }

    fun onNavigationHandled() {
        viewModelScope.launch {
            _navigationEvents.emit(null)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _questionList.value = quizRepository.getQuestionsByQuizId(quizId)
        }
    }

    fun saveAndGoBack(questionText: String, context: Context) {
        viewModelScope.launch {
            saveQuestion(questionText)
            Toast.makeText(
                context,
                "Question Updated",
                Toast.LENGTH_SHORT
            ).show()
            _navigationEvents.emit("back")
        }
    }

    fun selectAnswer(chosenQuestionId: Int, answerOptionId: Int) {
        if (questionId == chosenQuestionId) {
            _currentQuestionAnswer.value = answerOptionId
        }
    }

    fun updateAnswerOption(answerOptionId: Int, newText: String) {
        viewModelScope.launch {
            val answerOption = _question.value?.answerOptions?.find { it.uid == answerOptionId }
            answerOption?.text = newText
        }
    }

    fun saveQuiz(title: String, context: Context) {
        viewModelScope.launch {
            quiz.value?.quiz?.title = title
            quiz.value?.let { it.quiz?.let { it1 -> quizRepository.updateQuiz(it1) } }
            refresh()
            Toast.makeText(
                context,
                "Quiz Updated",
                Toast.LENGTH_SHORT
            ).show()
            _navigationEvents.emit("back")
        }
    }

    private fun saveQuestion(questionText: String) {
        viewModelScope.launch {
            val newQuiz = quiz.value
            if (newQuiz != null) {
                newQuiz.quiz?.title = _title.value.toString()
                newQuiz.quiz?.let { quizRepository.updateQuiz(it) }
            }

            _question.value?.question?.title = questionText
            _question.value?.question?.let { quizRepository.updateQuestion(it) }
            _question.value?.answerOptions?.forEach { answerOption ->
                answerOption.correct = answerOption.uid == _currentQuestionAnswer.value
                quizRepository.updateAnswerOption(answerOption)
            }
            quizRepository.updateQuestion(_question.value?.question!!)
        }
    }

    fun deleteQuiz(quizId: Int, context: Context) {
        viewModelScope.launch {
            quizRepository.deleteQuizById(quizId)
            refresh()
            Toast.makeText(
                context,
                "Quiz deleted",
                Toast.LENGTH_SHORT
            ).show()
            _navigationEvents.emit("back")
        }
    }

    fun deleteQuestion(questionId: Int, context: Context) {
        viewModelScope.launch {
            val question = quiz.value?.questions?.find { it.question.uid == questionId }
            question?.answerOptions?.forEach { answerOption ->
                quizRepository.deleteAnswerOptionById(answerOption.uid)
            }
            quizRepository.deleteQuestionById(questionId)
            _questionList.value = quizRepository.getQuestionsByQuizId(quizId)
            Toast.makeText(
                context,
                "Question deleted",
                Toast.LENGTH_SHORT
            ).show()
            _navigationEvents.emit("back")
        }

    }

    fun addNewQuestion(questionText: String) {

        val defaultAnswerOption = listOf(
            "Answer 1",
            "Answer 2",
            "Answer 3",
            "Answer 4"
        )

        viewModelScope.launch {
            val newQuestion = quiz.value?.quiz?.let {
                QuestionEntity(title = questionText, quizId = it.uid)
            }
            val newQuestionId = newQuestion?.let { quizRepository.insertQuestion(it) }
            val randomIndex = (0..3).random()

            val newWholeQuestion: WholeQuestion? = newQuestion?.let {
                newQuestionId?.let { it1 -> it.copy(uid = it1.toInt()) }?.let { it2 ->
                    WholeQuestion(
                        question = it2,
                        answerOptions = listOf()
                    )
                }
            }

            val answerOps = defaultAnswerOption.mapIndexed { index, answerText ->
                if (index == randomIndex) {
                    AnswerOptionEntity(
                        questionId = newQuestionId!!.toInt(),
                        text = answerText,
                        correct = true
                    )
                } else {
                    AnswerOptionEntity(
                        questionId = newQuestionId!!.toInt(),
                        text = answerText,
                    )
                }
            }

            quizRepository.insertAnswerOption(*answerOps.toTypedArray())
            _questionList.value += newWholeQuestion!!.question
            _navigationEvents.emit("editQuestion/${newWholeQuestion.question.uid}")

        }
    }
}