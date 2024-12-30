package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.dao.AnswerOptionDao
import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.data.dao.QuizDao
import com.example.quizapp.data.models.QuestionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditQuizViewModel @Inject constructor(
    private val questionDao: QuestionDao,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    val quizId: Int = savedStateHandle["quizId"]!!


    private val _questionList = MutableStateFlow<List<QuestionEntity>>(listOf())
    val questionList: StateFlow<List<QuestionEntity>> = _questionList

    init {
        viewModelScope.launch {
            _questionList.value = questionDao.getQuestionsByQuizId(quizId)
        }
    }

}