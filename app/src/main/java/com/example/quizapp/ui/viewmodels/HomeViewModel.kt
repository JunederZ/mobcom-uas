package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.dao.QuizDao
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.models.WholeQuiz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quizDao: QuizDao
) : ViewModel() {

    private val _navigationEvents = MutableLiveData<String>()
    val navigationEvents: LiveData<String> = _navigationEvents

    private val _quizList = MutableStateFlow<List<QuizEntity>>(listOf())
    val quizList: StateFlow<List<QuizEntity>> = _quizList

    private var _quiz: WholeQuiz? = null

    fun onClicked(quizId: Int) {
        viewModelScope.launch {
//            _quiz = quizDao.getWholeQuiz(quizId)
            _navigationEvents.value = "quiz/$quizId"
        }
    }

    init {
        viewModelScope.launch {
            _quizList.value = quizDao.getAllQuiz()
        }
    }
}