package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.ui.components.QuestionBox
import com.example.quizapp.ui.viewmodels.EditQuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuizScreen(
    navController: NavController,
    viewModel: EditQuizViewModel = hiltViewModel()
) {

    val questionList by viewModel.questionList.collectAsState()
    val navEvent by viewModel.navigationEvents.collectAsState(initial = null)
    var quizTitle by remember(viewModel.title) {
        mutableStateOf(viewModel.title.value)
    }

    LaunchedEffect(Unit) {
        viewModel.title.collect { initialTitle ->
            if (quizTitle.isEmpty()) {
                quizTitle = initialTitle
            }
        }
    }
    val context = LocalContext.current

    LaunchedEffect(navEvent) {
        navEvent?.let {
            when (it) {
                "back" -> navController.popBackStack()
                else -> navController.navigate(it)
            }
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }


    Scaffold(topBar = {
        TopAppBar(
            title = {
                TextField(
                    label = { Text("Quiz Name")},
                    value = quizTitle,
                    onValueChange = { quizTitle = it },
                    textStyle = TextStyle.Default.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                    ),
                )
            },
            modifier = Modifier, colors = TopAppBarColors(
                Color.Transparent,
                scrolledContainerColor = Color.Black,
                navigationIconContentColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black
            ), scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(questionList.size) { index ->
                    QuestionBox(questionList[index], index)
                }

            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier,
                    onClick = { viewModel.addNewQuestion("New Question") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add",
                        modifier = Modifier
                    )
                    Text("Add Question")
                }
                Button(
                    modifier = Modifier,
                    onClick = { viewModel.saveQuiz(quizTitle, context) }
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Save",
                        modifier = Modifier
                    )
                    Text("Save")
                }
                Button(
                    modifier = Modifier,
                    onClick = {
                        viewModel.deleteQuiz(
                            quizId = viewModel.quizId,
                            context = context
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete",
                        modifier = Modifier
                    )
                    Text("Delete Quiz")
                }
            }
        }


    }


}
