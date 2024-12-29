package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.quizapp.data.dao.QuizDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizDao: QuizDao
): ViewModel() {

}