package com.example.quizapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import com.example.quizapp.ui.theme.QuizappTheme
import com.example.quizapp.ui.viewmodels.HomeViewModel

@Composable
fun QuestionBox(
    quizId: Int,
    showEdit: Boolean = false,
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val navEvent by viewModel.navigationEvents.observeAsState()

    LaunchedEffect(navEvent) {
        navEvent?.let {
            navHostController.navigate(it)
        }
    }


    // Get Quiz Information
    // ----

    // Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(32.dp, 128.dp)
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
            onClick = { viewModel.onClicked(quizId) }
        ) {
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
                    Text("Quiz box $quizId")
                }

                AnimatedVisibility(
                    visible = showEdit,
                ) {
                    Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            onClick = {},
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

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun MainPage(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var showEdit by remember { mutableStateOf(false) }

    val quizList by viewModel.quizList.collectAsState()

    QuizappTheme  {
        Scaffold(
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
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color.Transparent,
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        TextButton(
                            onClick = {},
                            modifier = Modifier.background(Color.Black, RoundedCornerShape(100.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Text("Add New Quiz", color = Color.White)
                        }
                        TextButton(
                            onClick = {showEdit = !showEdit},
                            modifier = Modifier.background(Color.Black, RoundedCornerShape(100.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Text("Edit Quiz", color = Color.White)
                        }
                    }
                }
            }
        )
        { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier.padding(innerPadding)
            ) {
//                items(12) { index ->
//                    QuestionBox(index, showEdit)
//                }
                items(quizList.size) { index ->
                    QuestionBox(quizList[index].uid, showEdit, navHostController)
                }

            }

        }
    }
}