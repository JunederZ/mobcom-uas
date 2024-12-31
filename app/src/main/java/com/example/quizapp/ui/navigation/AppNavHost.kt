package com.example.quizapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizapp.ui.screens.EditQuizScreen
import com.example.quizapp.ui.screens.MainPage
import com.example.quizapp.ui.screens.QuizScreen
import com.example.quizapp.ui.screens.QuestionListScreen


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
            QuizScreen(quizId)
        }
        composable (Routes.EditQuiz.route) { navBackStackEntry ->
            val quizId = navBackStackEntry.arguments?.getString("quizId")
            quizId?.let {
                quizId.toIntOrNull()?.let { it1 ->
                    EditQuizScreen(it1, navController)
                }
            }
        }
        composable(Routes.MainPage.route) {
            MainPage(navController)
        }
        composable(Routes.QuestionListScreen.route) {
            QuestionListScreen(onBackClick = { /* Handle back navigation */ })
        }
    }
}