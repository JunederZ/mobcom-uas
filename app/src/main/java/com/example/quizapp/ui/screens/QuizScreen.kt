package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quizapp.ui.components.FinishAlert
import com.example.quizapp.ui.components.Question
import com.example.quizapp.ui.viewmodels.QuizViewModel


@Composable
fun QuizScreen(
    quizId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
) {

    val quiz by viewModel.quiz.collectAsState()
    val index by viewModel.questionIndex.collectAsState()
    val isQuizComplete by viewModel.isQuizComplete.collectAsState()
    val length: Int

    val navigateToResult by viewModel.navigateToResult.collectAsState()

    LaunchedEffect(navigateToResult) {
        if (navigateToResult) {
            navController.navigate("result")
        }
    }

    if (quiz.questions == null) {
        CircularProgressIndicator()
    } else {
        length = quiz.questions!!.size
        val nextText =
            if (index + 1 != length) "Next"
            else if (isQuizComplete) "Result"
            else "Finish"
        val nextCallback =
            if (index + 1 != length) viewModel::nextQuestion
            else if (isQuizComplete) viewModel::toResult
            else viewModel::finishModal

        FinishAlert()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Question()
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    modifier = Modifier,
                    onClick = viewModel::prevQuestion
                ) {
                    Text("Prev")
                }
                Button(
                    modifier = Modifier,
                    onClick = {  }
                ) {
                    Text("Questions")
                }
                Button(
                    modifier = Modifier,
                    onClick = nextCallback
                ) {
                    Text(nextText)
                }
            }

        }
    }


}