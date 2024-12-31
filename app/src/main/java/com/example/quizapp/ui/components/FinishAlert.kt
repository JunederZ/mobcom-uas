package com.example.quizapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.viewmodels.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishAlert(
    viewModel: QuizViewModel = hiltViewModel()
) {

    val dialogVisibility by viewModel.dialogVisibility.collectAsState()
    if (dialogVisibility ) {
        BasicAlertDialog(onDismissRequest = viewModel::dismissDialog) {
            Surface(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Finish Quiz",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text =
                        "Proceed to finish the quiz. Double check your answers before continue",
                    )

                    if (viewModel.canFinishQuiz()) {
                        Text("You haven't answered all question. Finish anyway?")
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = viewModel::dismissDialog,
                        ) {
                            Text("Cancel", color = MaterialTheme.colorScheme.error)
                        }
                        TextButton(
                            onClick = viewModel::finishQuiz,
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}