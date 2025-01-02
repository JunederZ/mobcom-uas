package com.example.quizapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.ui.viewmodels.EditQuizViewModel


@Composable
fun QuestionBox(
    question: QuestionEntity,
    index: Int,
    viewModel: EditQuizViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { viewModel.navigateToEditQuestion(question.uid) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (index + 1).toString(),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}