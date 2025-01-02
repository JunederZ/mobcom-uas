package com.example.quizapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quizapp.ui.theme.success
import com.example.quizapp.ui.viewmodels.QuizViewModel

@Composable
fun ResultScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val quiz by viewModel.quiz.collectAsState()
    val total = quiz.questions!!.size
    val correct by viewModel.correctCount.collectAsState()
    val score by viewModel.score.collectAsState()

    val navigateToReview by viewModel.navigateFromResult.collectAsState()


    LaunchedEffect(navigateToReview) {
        if (navigateToReview == "review") {
            navController.popBackStack()
        }
        if (navigateToReview == "home") {
            navController.navigate("home") {
                popUpTo(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Result Of Your Quiz",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 200.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "You Have Scored",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "$score%",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuizStat(
                        modifier = Modifier
                            .weight(1f)
                            .padding(18.dp),
                        prefix = "Q",
                        value = total.toString(),
                        label = "Questions",
                        textColor = MaterialTheme.colorScheme.primary
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .height(100.dp)
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    QuizStat(
                        modifier = Modifier
                            .weight(1f)
                            .padding(18.dp),
                        prefix = "✓",
                        value = correct.toString(),
                        label = "Correct",
                        textColor = MaterialTheme.colorScheme.success
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .height(100.dp)
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    QuizStat(
                        modifier = Modifier
                            .weight(1f)
                            .padding(18.dp),
                        prefix = "✕",
                        value = (total - correct!!).toString(),
                        label = "Wrong",
                        textColor = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = viewModel::toHome,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Back To Home",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Button(
            onClick = viewModel::toReview,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Review",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun QuizStat(
    modifier: Modifier = Modifier,
    prefix: String,
    value: String,
    label: String,
    textColor: Color
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = prefix,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}
