package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quizapp.ui.components.QuestionBox
import com.example.quizapp.ui.viewmodels.EditQuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuizScreen(
    quizId: Int,
    navController: NavController,
    viewModel: EditQuizViewModel = hiltViewModel()
) {

    val questionList by viewModel.questionList.collectAsState()
    val navEvent by viewModel.navigationEvents.collectAsState(initial = null)

    LaunchedEffect(navEvent) {
        navEvent?.let { route ->
            navController.navigate(route)
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Quzap",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }, modifier = Modifier, colors = TopAppBarColors(
                MaterialTheme.colorScheme.primaryContainer,
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
            TextButton(modifier = Modifier.align(Alignment.BottomCenter), onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    modifier = Modifier
                )
                Text("Add Question")
            }
        }


    }


}
