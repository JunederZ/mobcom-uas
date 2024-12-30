package com.example.quizapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.ui.components.QuestionBox
import com.example.quizapp.ui.theme.QuizappTheme
import com.example.quizapp.ui.viewmodels.EditQuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuizScreen(
    quizId: Int,
    navController: NavController,
    viewModel: EditQuizViewModel = hiltViewModel()
) {
    val questionList by viewModel.questionList.collectAsState()

    QuizappTheme {

        Scaffold (
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Quzap",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier,
                    colors = TopAppBarColors(
                        MaterialTheme.colorScheme.primaryContainer,
                        scrolledContainerColor = Color.Black,
                        navigationIconContentColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.Black,
                        actionIconContentColor = Color.Black
                    ),
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                )
            }
        ) { innerPadding ->

            LazyVerticalGrid(
                columns = GridCells.FixedSize(480.dp),
                modifier = Modifier.padding(innerPadding)
            ) {
                items(questionList.size) { index ->
                    QuestionBox(questionList[index], navController)
                }
            }


        }

    }


}
