package com.example.quizapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizapp.ui.screens.EditQuestionScreen
import com.example.quizapp.ui.screens.EditQuizScreen
import com.example.quizapp.ui.screens.MainPage
import com.example.quizapp.ui.screens.QuizScreen
import com.example.quizapp.ui.screens.QuestionListScreen
import com.example.quizapp.ui.screens.ResultScreen
import com.example.quizapp.ui.viewmodels.QuizViewModel


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
//            val viewModel: QuizViewModel =
//                if (navController.previousBackStackEntry != null) hiltViewModel(
//                    navController.previousBackStackEntry!!
//                ) else hiltViewModel(backStackEntry)
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.Quiz.route)
            }
            val viewModel: QuizViewModel = hiltViewModel(parentEntry)
//            val viewModel: QuizViewModel = hiltViewModel(backStackEntry)
            val quizId = backStackEntry.arguments!!.getInt("quizId")
            QuizScreen(quizId, navController, viewModel=viewModel)
        }
        composable(Routes.Result.route) { backStackEntry ->
//            val viewModel: QuizViewModel =
//                if (navController.previousBackStackEntry != null) hiltViewModel(
//                    navController.previousBackStackEntry!!
//                ) else hiltViewModel()
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.Quiz.route)
            }
            val viewModel: QuizViewModel = hiltViewModel(parentEntry)
            ResultScreen(viewModel=viewModel, navController)
        }
        composable (Routes.EditQuiz.route) { navBackStackEntry ->
            val quizId = navBackStackEntry.arguments?.getString("quizId")
            quizId?.let {
                quizId.toIntOrNull()?.let { it1 ->
                    EditQuizScreen(it1, navController)
                }
            }
        }

        composable (Routes.EditQuestion.route) { navBackStackEntry ->
            val quizId = navBackStackEntry.arguments?.getString("questionId")
            quizId?.let {
                quizId.toIntOrNull()?.let { it1 ->
                    EditQuestionScreen(it1, navController)
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