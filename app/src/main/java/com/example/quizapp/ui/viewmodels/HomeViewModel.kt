package com.example.quizapp.ui.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.repositories.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _quizList = MutableStateFlow<List<QuizEntity>>(listOf())
    val quizList: StateFlow<List<QuizEntity>> = _quizList

    private val _navigationEvents = MutableStateFlow<String?>(null)
    val navigationEvents = _navigationEvents.asStateFlow()

    fun navigateToEditQuiz(quizId: Int) {
        _navigationEvents.value = "editQuiz/$quizId"
    }

    fun navigateToQuiz(quizId: Int, context: Context) {
        viewModelScope.launch {
            val quiz = quizRepository.getWholeQuiz(quizId)
            if (quiz.questions?.isEmpty() == true) {
                Toast.makeText(context, "This quiz has no question", Toast.LENGTH_SHORT).show()
                return@launch
            }
            _navigationEvents.value = "quiz/$quizId"
        }
    }

    fun addNewQuiz() {
        viewModelScope.launch {
            val newQuiz = QuizEntity(title = "New Quiz")
            val newQuizId = quizRepository.insertQuiz(newQuiz)
            _navigationEvents.value = "editQuiz/$newQuizId"
        }
    }

    fun onNavigated() {
        _navigationEvents.value = null
    }

    fun refresh() {
        viewModelScope.launch {
            _quizList.value = quizRepository.getAllQuiz()
        }
    }

    init {
        viewModelScope.launch {
            _quizList.value = quizRepository.getAllQuiz()
        }
    }
}