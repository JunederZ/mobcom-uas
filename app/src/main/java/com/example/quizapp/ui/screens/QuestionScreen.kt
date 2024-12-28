package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.components.AnswerOption


@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
//            .background(Color.Red)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
        ) {
        Box (
            modifier = modifier
                .fillMaxWidth(),
//                .background(Color.Blue),
            contentAlignment = Alignment.TopCenter
        ) {
            Column (
            )  {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom

                ) {
                    Text("Quiz A", style = MaterialTheme.typography.headlineMedium)
                    Text("Question 1 of 4")
                }
                Spacer(modifier=Modifier.height(8.dp))


                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = {0.25f}
                )
                Spacer(modifier=Modifier.height(16.dp))


                Text("Question Body: Lorem ipsum dolor sit amet", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier=Modifier.height(16.dp))
                // answer choice
                Column (
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AnswerOption()
                    AnswerOption()
                    AnswerOption()
                    AnswerOption()
                }
            }

        }

        Row(
            modifier = Modifier
//                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { /* previous */ }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous"
                )
            }

            IconButton(
                onClick = { /* next */ }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next"
                )
            }
        }

    }

}