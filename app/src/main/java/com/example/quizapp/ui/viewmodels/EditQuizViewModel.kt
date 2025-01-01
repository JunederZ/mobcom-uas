package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.models.QuestionEntity
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

    val quizId: Int = savedStateHandle.get<String>("quizId")?.toInt() ?: -1

    private val _navigationEvents = MutableSharedFlow<String?>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val _questionList = MutableStateFlow<List<QuestionEntity>>(listOf())
    val questionList: StateFlow<List<QuestionEntity>> = _questionList

    init {
        viewModelScope.launch {
            _questionList.value = quizRepository.getQuestionsByQuizId(quizId)
            _navigationEvents.emit(null)

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

}