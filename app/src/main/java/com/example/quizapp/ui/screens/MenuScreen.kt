package com.example.quizapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quizapp.R
import com.example.quizapp.ui.components.QuizBox

import com.example.quizapp.ui.theme.AppTheme
import com.example.quizapp.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    var showEdit by remember { mutableStateOf(false) }
    val quizList by viewModel.quizList.collectAsState()
    val navEvent by viewModel.navigationEvents.collectAsState(initial = "")

    LaunchedEffect(navEvent) {
        navEvent?.let {
            navHostController.navigate(it)
            viewModel.onNavigated()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        ImageVector.vectorResource(R.drawable.iconn), "Icon",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraLarge)
                            .width(52.dp)
                            .height(52.dp)
                            .background(Color.White)
                            .padding(8.dp)


                    )
                },
                modifier = Modifier,
//                colors = TopAppBarColors(
//                    MaterialTheme.colorScheme.primaryContainer,
//                    scrolledContainerColor = Color.Black,
//                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = Color.Black,
//                    actionIconContentColor = Color.Black
//                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically),

                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    TextButton(
                        onClick = { viewModel.addNewQuiz() },
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(100.dp)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text("Add New Quiz", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    TextButton(
                        onClick = { showEdit = !showEdit },
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(100.dp)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text("Edit Quiz", color = MaterialTheme.colorScheme.onPrimaryContainer)
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
            items(quizList.size, key = { quizList[it].uid }) { index ->
                QuizBox(quizList[index], showEdit)
            }

        }

    }

}