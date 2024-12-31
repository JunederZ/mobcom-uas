package com.example.quizapp.ui.navigation

sealed class Routes(val route: String) {
    data object MainPage : Routes("home")
    data object Quiz : Routes("quiz/{quizId}")
    data object EditQuiz : Routes("editQuiz/{quizId}")
    data object EditQuestion : Routes("editQuestion/{questionId}")
}