package com.example.quizapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.WholeQuestion
import com.example.quizapp.data.models.WholeQuiz
import com.example.quizapp.ui.components.AnswerOption
import com.example.quizapp.ui.components.Question
import com.example.quizapp.ui.viewmodels.EditQuestionViewModel
import com.example.quizapp.ui.viewmodels.EditQuizViewModel
import com.example.quizapp.ui.viewmodels.QuizViewModel

@Composable
fun EditQuestionScreen(
    questionId: Int,
    viewModel: EditQuestionViewModel = hiltViewModel(),
) {

    val quiz by viewModel.quiz.collectAsState(initial = null)
    var title: String = null.toString()
    var questionAnswers: WholeQuestion? = null


    when (quiz) {
        null -> CircularProgressIndicator()
        else -> {
             title = quiz?.quiz?.title ?: ""
            questionAnswers = quiz?.questions?.find { it.question.uid == questionId }
        }
    }



    val currentAnswer by viewModel.currentQuestionAnswer.collectAsState()

    var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }

    if (quiz?.questions == null) {
        CircularProgressIndicator()
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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
                    }
                    Spacer(modifier= Modifier.height(8.dp))


                    Spacer(modifier= Modifier.height(24.dp))


                    questionAnswers.let { wholeQuestion ->
                        if (wholeQuestion != null) {
                            Text(
                                wholeQuestion.question.title,
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            wholeQuestion?.answerOptions?.forEach { option ->
                                val selected = option.uid == currentAnswer

                                AnswerOption(
                                    optionText = option.text,
                                    answerId = option.uid,
                                    questionId = wholeQuestion.question.uid,
                                    selected = selected,
                                    onSelect = viewModel::selectAnswer,
                                )
                            }
                        }
                    }
                }

            }
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
//            IconButton(
//                onClick = { viewModel.nextQuestion() }
//            ) {
//                Icon(
//                    modifier = Modifier.size(48.dp),
//                    imageVector = Icons.Default.KeyboardArrowRight,
//                    contentDescription = "Next"
//                )
//            }
            }

        }
    }


}