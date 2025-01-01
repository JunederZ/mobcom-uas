package com.example.quizapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.ui.viewmodels.HomeViewModel

@Composable
fun QuizBox(
    quiz: QuizEntity,
    showEdit: Boolean = false,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(32.dp, 128.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(16.dp),
            onClick = { viewModel.navigateToQuiz(quiz.uid) }) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(quiz.title)
                }

                AnimatedVisibility(
                    visible = showEdit,
                ) {
                    Box(
                        modifier = Modifier, contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            onClick = { viewModel.navigateToEditQuiz(quiz.uid) },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        ) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }
}