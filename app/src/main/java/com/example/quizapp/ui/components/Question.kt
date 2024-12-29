package com.example.quizapp.ui.components

import android.health.connect.datatypes.units.Length
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.viewmodels.QuizViewModel

@Composable
fun Question(
    viewModel: QuizViewModel = hiltViewModel()
) {
    val quiz by viewModel.quiz.collectAsState()
    val index by viewModel.questionIndex.collectAsState()
    val progress by viewModel.progress.collectAsState()

    val title = quiz.quiz!!.title
    val length = quiz.questions!!.size
    val question = quiz.questions!![index].question.title
    val answerOptions = quiz.questions!![index].answerOptions.map { it.text }


    Box (
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column (
        )  {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom

            ) {
                Text(title)
                Text("Question ${index + 1} of $length")
            }
            Spacer(modifier= Modifier.height(8.dp))


            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = {progress}
            )
            Spacer(modifier= Modifier.height(24.dp))


            Text(
                question,
                style = MaterialTheme.typography.titleLarge,

                )
            Spacer(modifier= Modifier.height(32.dp))
            Column (
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                answerOptions.forEach{ opt ->
                    AnswerOption(opt)
                }
            }
        }

    }
}