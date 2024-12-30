package com.example.quizapp.ui.navigation

sealed class Routes(val route: String) {
    data object MainPage : Routes("home")
    data object Quiz : Routes("quiz/{quizId}")
    data object QuestionListScreen : Routes("edi")
}