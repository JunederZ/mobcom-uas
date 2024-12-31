package com.example.quizapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.data.dao.AnswerOptionDao
import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.data.dao.QuizDao
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.models.WholeQuiz
import com.example.quizapp.data.repositories.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditQuestionViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {


    private val _currentQuestionAnswer = MutableStateFlow<Int?>(null)
    val currentQuestionAnswer: StateFlow<Int?> = _currentQuestionAnswer

    private val _navigationEvents = MutableLiveData<String>()
    val navigationEvents: LiveData<String> = _navigationEvents


    private val _questionList = MutableStateFlow<List<QuestionEntity>>(listOf())
    val questionList: StateFlow<List<QuestionEntity>> = _questionList


    private val _selectedAnswers = MutableStateFlow(mutableMapOf<Int, Int>())
    val selectedAnswers: StateFlow<Map<Int, Int>> = _selectedAnswers

    private val _correctAnswer = MutableStateFlow<Int?>(null)
    val correctAnswer: StateFlow<Int?> = _correctAnswer

    private val _questionIndex = MutableStateFlow(0)
    val questionIndex: StateFlow<Int> = _questionIndex

    fun selectAnswer(questionId: Int, answerOptionId: Int) {
        Log.d("EditQuestionVM", "Selected answer: $answerOptionId for question: $questionId")
        _selectedAnswers.value = _selectedAnswers.value.toMutableMap().apply {
            put(questionId, answerOptionId)
        }
        if (quiz.value?.questions?.get(questionIndex.value)?.question?.uid == questionId) {
            _currentQuestionAnswer.value = answerOptionId
            _correctAnswer.value = answerOptionId
        }
    }
    val questionId: Int = savedStateHandle.get<String>("questionId")?.toInt() ?: -1

    private val _quiz = MutableStateFlow<WholeQuiz?>(null)
    val quiz: StateFlow<WholeQuiz?> = _quiz

    init {
        viewModelScope.launch {
            try {
                Log.d("EditQuestionVM", "Loading quiz for questionId: $questionId")
                _quiz.value = quizRepository.getQuizByQuestionId(questionId)

                _currentQuestionAnswer.value = quiz.value?.questions?.find { it.question.uid == questionId }?.answerOptions?.find { it.correct }?.uid
            } catch (e: Exception) {
                Log.e("EditQuizVM", "Error loading quiz", e)
            }
        }
    }

    fun saveQuestion() {
        viewModelScope.launch {
            quiz.value?.quiz?.let { quizRepository.insertQuiz(it) }
            quiz.value?.questions?.forEach{ question ->
                quizRepository.insertQuestion(question.question)
                question.answerOptions.forEach { answerOption ->
                    quizRepository.insertAnswerOption(answerOption)
                }
            }
        }
    }

}