package com.example.quizapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.dao.AnswerOptionDao
import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.data.dao.QuizDao
import com.example.quizapp.data.database.AppDatabase
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.models.WholeQuestion
import com.example.quizapp.data.models.WholeQuiz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizDao: QuizDao,
    private val questionDao: QuestionDao,
    private val answerOptionDao: AnswerOptionDao,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    val quizId: Int = savedStateHandle["quizId"]!!

    private val _questionIndex = MutableStateFlow(0)
    val questionIndex: StateFlow<Int> = _questionIndex

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    private val _quiz = MutableStateFlow(
        WholeQuiz(null, null)
    )
    val quiz: StateFlow<WholeQuiz> = _quiz

    private val _isQuizComplete = MutableStateFlow(false)
    val isQuizComplete: StateFlow<Boolean> = _isQuizComplete

    private val _score = MutableStateFlow<Float?>(null)
    val score: StateFlow<Float?> = _score

    private val _selectedAnswers = MutableStateFlow(mutableMapOf<Int, Int>())
    val selectedAnswers: StateFlow<Map<Int, Int>> = _selectedAnswers

    private val _currentQuestionAnswer = MutableStateFlow<Int?>(null)
    val currentQuestionAnswer: StateFlow<Int?> = _currentQuestionAnswer

    private val _dialogVisibility = MutableStateFlow(false)
    val dialogVisibility: StateFlow<Boolean> = _dialogVisibility


    fun selectAnswer(questionId: Int, answerOptionId: Int) {
        _selectedAnswers.value = _selectedAnswers.value.toMutableMap().apply {
            put(questionId, answerOptionId)
        }
        if (quiz.value.questions?.get(questionIndex.value)?.question?.uid == questionId) {
            _currentQuestionAnswer.value = answerOptionId
        }
    }

    fun dismissDialog() {
        _dialogVisibility.value = false
    }

    fun canFinishQuiz(): Boolean {
        return _quiz.value.questions?.size == _selectedAnswers.value.size
    }

    fun finishModal() {
        if (!canFinishQuiz()) return
        _dialogVisibility.value = true

    }

    fun finishQuiz() {

//        viewModelScope


        viewModelScope.launch {
            var correctAnswers = 0
            _quiz.value.questions?.forEach { wholeQuestion ->
                val selectedAnswerId = _selectedAnswers.value[wholeQuestion.question.uid]
                val correctAnswer = wholeQuestion.answerOptions.find { it.correct }

                if (selectedAnswerId != null && correctAnswer != null &&
                    selectedAnswerId == correctAnswer.uid) {
                    correctAnswers++
                }
            }

            _score.value = correctAnswers.toFloat() / _quiz.value.questions!!.size
            Log.d("SCORE", _score.value.toString())
            _isQuizComplete.value = true
        }
    }


    init {
        viewModelScope.launch {
            _quiz.value = quizDao.getWholeQuiz(quizId)
            _progress.value = ((_questionIndex.value.toFloat() + 1) / _quiz.value.questions!!.size)
            updateCurrentQuestionAnswer()
        }
    }

    fun nextQuestion() {
        if (_questionIndex.value < _quiz.value.questions!!.size - 1) {
            _questionIndex.value++
            updateCurrentQuestionAnswer()
        }
        _progress.value = ((_questionIndex.value.toFloat() + 1) / _quiz.value.questions!!.size)
    }

    fun prevQuestion() {
        if (_questionIndex.value > 0) {
            _questionIndex.value--
            updateCurrentQuestionAnswer()
        }
        _progress.value = ((_questionIndex.value.toFloat() + 1) / _quiz.value.questions!!.size)
    }


    private fun updateCurrentQuestionAnswer() {
        val currentQuestionId = _quiz.value.questions?.get(_questionIndex.value)?.question?.uid
        _currentQuestionAnswer.value = currentQuestionId?.let { _selectedAnswers.value[it] }
    }



    private suspend fun populateDatabase() {
        val quiz = QuizEntity(title = "General Knowledge")
        val quizId = quizDao.insertQuiz(quiz)

        val questionsAndAnswers = listOf(
            "What is the capital of France?" to listOf("Paris", "London", "Berlin", "Rome"),
            "What is 2 + 2?" to listOf("3", "4", "5", "6"),
            "What is the largest planet?" to listOf("Mars", "Earth", "Jupiter", "Venus"),
            "Who wrote 'Romeo and Juliet'?" to listOf(
                "Shakespeare",
                "Hemingway",
                "Austen",
                "Tolstoy"
            ),
            "Which is the longest river in the world?" to listOf(
                "Amazon",
                "Nile",
                "Yangtze",
                "Mississippi"
            ),
            "What is the smallest prime number?" to listOf("1", "2", "3", "5"),
            "What color is a ripe banana?" to listOf("Red", "Yellow", "Green", "Blue"),
            "What is the square root of 16?" to listOf("2", "4", "8", "16"),
            "What is the chemical symbol for water?" to listOf("H2O", "O2", "CO2", "HO2"),
            "What is the capital of Japan?" to listOf("Seoul", "Tokyo", "Beijing", "Bangkok")
        )

        for ((questionText, answers) in questionsAndAnswers) {
            val questionId = questionDao.insertQuestion(
                QuestionEntity(title = questionText, quizId = quizId.toInt())
            )

            val isCorrect = Random.nextInt(0,3)
            val answerOptions = answers.mapIndexed { index, answerText ->
                if (isCorrect == index) {
                    AnswerOptionEntity(questionId = questionId.toInt(), text = answerText, correct = true)
                } else {
                    AnswerOptionEntity(questionId = questionId.toInt(), text = answerText)
                }
            }

            answerOptionDao.insertAnswerOption(*answerOptions.toTypedArray())
        }
    }
}