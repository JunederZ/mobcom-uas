package com.example.quizapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.ui.viewmodels.EditQuizViewModel

@Composable
fun QuestionBox(
    question: QuestionEntity,
    viewModel: EditQuizViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(16.dp),
            onClick = { viewModel.navigateToEditQuestion(question.uid) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        question.title
                    )
                }

            }
        }
    }
}