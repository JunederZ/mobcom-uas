package com.example.quizapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quizapp.data.models.WholeQuestion
import com.example.quizapp.ui.components.EditAnswerOption
import com.example.quizapp.ui.viewmodels.EditQuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun EditQuestionScreen(
    questionId: Int,
    navHostController: NavHostController,
    viewModel: EditQuizViewModel = hiltViewModel(),
) {

    val quiz by viewModel.quiz.collectAsState(initial = null)
    var questionAnswers: WholeQuestion? = null
    val currentAnswer by viewModel.currentQuestionAnswer.collectAsState()
    var question by remember { mutableStateOf("") }
    var optionTexts by remember { mutableStateOf(mutableMapOf<Int, String>()) }
    val navEvent by viewModel.navigationEvents.collectAsState(null)
    val context = LocalContext.current

    LaunchedEffect(navEvent) {
        navEvent?.let {
            when (it) {
                "back" -> navHostController.popBackStack()
                else -> navHostController.navigate(it)
            }
        }
    }

    LaunchedEffect(quiz) {
        quiz?.questions?.find { it.question.uid == questionId }?.let { wholeQuestion ->
            if (question.isEmpty()) {
                question = wholeQuestion.question.title
            }
            wholeQuestion.answerOptions.forEach { option ->
                optionTexts = optionTexts.toMutableMap().apply {
                    put(option.uid, option.text)
                }
            }
            questionAnswers = wholeQuestion
        }
    }

    when (quiz) {
        null -> CircularProgressIndicator()
        else -> {
            questionAnswers = quiz?.questions?.find { it.question.uid == questionId }
        }
    }

    if (quiz?.questions == null) {
        CircularProgressIndicator()
    } else {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom

                        ) {
                            Text(quiz!!.quiz!!.title)
                        }
                        Spacer(modifier = Modifier.height(8.dp))


                        Spacer(modifier = Modifier.height(24.dp))


                        questionAnswers.let { wholeQuestion ->
                            TextField(
                                question,
                                label = {Text("Question")},
                                onValueChange = { newText ->
                                    question = newText
                                },
                                textStyle = MaterialTheme.typography.titleLarge,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    errorContainerColor = Color.Transparent,
                                ),
                                placeholder = { Text("Enter question body") }
                            )

                            viewModel.updateQuestionText(wholeQuestion?.question?.title ?: "")

                            Spacer(modifier = Modifier.height(32.dp))
                            Column(
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                wholeQuestion?.answerOptions?.forEach { option ->
                                    val selected = option.uid == currentAnswer
                                    val thisOptionText = optionTexts[option.uid] ?: ""

                                    EditAnswerOption(
                                        optionText = thisOptionText,
                                        answerId = option.uid,
                                        questionId = wholeQuestion.question.uid,
                                        selected = selected,
                                        onSelect = viewModel::selectAnswer,
                                        onChange = { newText ->
                                            optionTexts = optionTexts.toMutableMap().apply {
                                                put(option.uid, newText)
                                            }
                                            viewModel.updateAnswerOption(option.uid, newText)
                                        },
                                    )
                                }
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            TextButton(
                                onClick = {
                                    viewModel.saveAndGoBack(question, context)
                                },
                            ) {
                                Text("Save")
                            }
                            TextButton(
                                onClick = {
                                    viewModel.deleteQuestion(questionId, context)
                                },
                            ) {
                                Text("Delete")
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
                }

            }

    }


}