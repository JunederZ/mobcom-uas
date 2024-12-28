package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.components.AnswerCard


@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            Text("Question Body: Lorem ipsum dolor sit amet")
            Spacer(modifier=Modifier.height(16.dp))
            // answer choice
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AnswerCard()
                AnswerCard()
                AnswerCard()
                AnswerCard()
            }
        }

    }
}