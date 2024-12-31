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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.components.FinishAlert
import com.example.quizapp.ui.components.Question
import com.example.quizapp.ui.viewmodels.QuizViewModel


@Composable
fun QuizScreen(
    quizId: Int,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
) {

    val quiz by viewModel.quiz.collectAsState()
    val index by viewModel.questionIndex.collectAsState()
    val length: Int

    if (quiz.questions == null) {
        CircularProgressIndicator()
    } else {
        length = quiz.questions!!.size
        val nextText = if (index + 1 == length) "Finish" else "Next"
        val nextCallback =
            if (index + 1 == length) viewModel::finishModal else viewModel::nextQuestion

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
//            IconButton(
//                onClick = { viewModel.prevQuestion() }
//            ) {
//                Icon(
//                    modifier = Modifier.size(48.dp),
//                    imageVector = Icons.Default.KeyboardArrowLeft,
//                    contentDescription = "Previous"
//                )
//            }
                Button(
                    modifier = Modifier,
                    onClick = { viewModel.prevQuestion() }
                ) {
                    Text("Prev")
                }

//            IconButton(
//                onClick = { viewModel.nextQuestion() }
//            ) {
//                Icon(
//                    modifier = Modifier.size(48.dp),
//                    imageVector = Icons.Default.KeyboardArrowRight,
//                    contentDescription = "Next"
//                )
//            }
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