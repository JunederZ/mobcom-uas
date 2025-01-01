package com.example.quizapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.models.AnswerOptionEntity
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
class EditQuestionViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    private val _questionText = MutableLiveData<String>()
    val questionText: LiveData<String> = _questionText

    fun updateQuestionText(newText: String) {
        _questionText.value = newText
    }

    private val _question = MutableLiveData<WholeQuestion>()
    val question: LiveData<WholeQuestion> = _question

    private val _answerOptions = MutableLiveData<List<AnswerOptionEntity>>()
    val answerOptions: MutableLiveData<List<AnswerOptionEntity>> = _answerOptions

    fun updateAnswerOption(answerOptionId: Int, newText: String) {
        viewModelScope.launch {
            val answerOption = _question.value?.answerOptions?.find { it.uid == answerOptionId }
            answerOption?.text = newText
            Log.d("EditQuestionViewModel", "Answer option updated: $answerOption")
        }
    }

    private val _currentQuestionAnswer = MutableStateFlow<Int?>(null)
    val currentQuestionAnswer: StateFlow<Int?> = _currentQuestionAnswer

    private val _navigationEvents = MutableLiveData<String>()
    val navigationEvents: LiveData<String> = _navigationEvents

    private val _correctAnswer = MutableStateFlow<Int?>(null)
    val correctAnswer: StateFlow<Int?> = _correctAnswer

    val questionId: Int = savedStateHandle.get<String>("questionId")?.toInt() ?: -1

    private val _quiz = MutableStateFlow<WholeQuiz?>(null)
    val quiz: StateFlow<WholeQuiz?> = _quiz

    fun selectAnswer(_questionId: Int, answerOptionId: Int) {
        if (questionId == _questionId) {
            _currentQuestionAnswer.value = answerOptionId
            _correctAnswer.value = answerOptionId
        }
    }

    init {
        viewModelScope.launch {
            _quiz.value = quizRepository.getQuizByQuestionId(questionId)
            _currentQuestionAnswer.value =
                quiz.value?.questions?.find { it.question.uid == questionId }?.answerOptions?.find { it.correct }?.uid
            _question.value = quiz.value?.questions?.find { it.question.uid == questionId }
        }
    }

    fun saveQuestion() {
        viewModelScope.launch {
            val newQuiz = quiz.value
            if (newQuiz != null) {
                newQuiz.quiz?.title = _title.value.toString()
                newQuiz.quiz?.let { quizRepository.updateQuiz(it) }
            }

            _question.value?.question?.title = _questionText.value.toString()
            _question.value?.question?.let { quizRepository.updateQuestion(it) }
            _question.value?.answerOptions?.forEach { answerOption ->
                answerOption.correct = answerOption.uid == _currentQuestionAnswer.value
                quizRepository.updateAnswerOption(answerOption)
            }
            quizRepository.updateQuestion(_question.value?.question!!)
        }
    }

}