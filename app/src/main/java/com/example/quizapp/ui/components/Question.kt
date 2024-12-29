package com.example.quizapp.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Question() {
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
                Text("Quiz A", style = MaterialTheme.typography.titleLarge)
                Text("Question 1 of 4")
            }
            Spacer(modifier= Modifier.height(8.dp))


            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = {0.25f}
            )
            Spacer(modifier= Modifier.height(24.dp))


            Text(
                "Title",
                style = MaterialTheme.typography.displayMedium,

                )
            Spacer(modifier= Modifier.height(32.dp))
            Column (
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                listOf("Answer","Answer").forEach{ opt ->
                    AnswerOption(opt)
                }
            }
        }

    }
}