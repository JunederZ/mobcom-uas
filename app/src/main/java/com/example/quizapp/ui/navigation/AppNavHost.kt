package com.example.quizapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizapp.ui.screens.MainPage
import com.example.quizapp.ui.screens.QuestionScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val initialRoute = "home"

    NavHost(navController, startDestination = initialRoute) {
        composable(
            Routes.Quiz.route,
            arguments = listOf(
                navArgument("quizId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val quizId = backStackEntry.arguments!!.getInt("quizId")
            QuestionScreen(quizId)
        }
        composable(Routes.MainPage.route) {
            MainPage(navController)
        }
    }
}