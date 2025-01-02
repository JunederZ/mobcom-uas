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
    val progress by viewModel.progress.collectAsState()
    val quiz by viewModel.quiz.collectAsState()
    val title = quiz.quiz!!.title
    val index by viewModel.questionIndex.collectAsState()

    val length = quiz.questions!!.size
    val isQuizComplete by viewModel.isQuizComplete.collectAsState()
    val currentAnswer by viewModel.currentQuestionAnswer.collectAsState()

    val currentQuestion = quiz.questions?.get(index)

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom

            ) {
                Text(title)
                Text("Question ${index + 1} of $length")
            }
            Spacer(modifier = Modifier.height(8.dp))


            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = { progress }
            )
            Spacer(modifier = Modifier.height(24.dp))


            currentQuestion?.let { wholeQuestion ->
                Text(
                    wholeQuestion.question.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    wholeQuestion.answerOptions.forEach { option ->
                        val selected = option.uid == currentAnswer
                        val isCorrect = if (isQuizComplete) {
                            option.correct
                        } else null

                        AnswerOption(
                            optionText = option.text,
                            answerId = option.uid,
                            questionId = wholeQuestion.question.uid,
                            selected = selected,
                            onSelect = viewModel::selectAnswer,
                            isCorrect = isCorrect,
                            index = index,
                        )
                    }
                }
            }
        }

    }
}