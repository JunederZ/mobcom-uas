package com.example.quizapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizapp.ui.screens.QuestionScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val initialRoute = "home"

    NavHost(navController, startDestination = initialRoute) {
        composable(Routes.Quiz.route) {
            QuestionScreen()
        }
    }
}