package com.example.quizapp.ui.navigation

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Quiz : Routes("quiz")
}