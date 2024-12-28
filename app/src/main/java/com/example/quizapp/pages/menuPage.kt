package com.example.quizapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuestionBox(quizId: Int) {

    // Get Quiz Information
    // ----

    // Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(32.dp, 128.dp)
            .padding(8.dp)
            .fillMaxSize()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RectangleShape),
            colors = ButtonColors(
                Color.LightGray,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(16.dp),

            onClick = {}
        ) {
            Text("Quiz box $quizId")

        }
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainPage() {

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Quzap",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                },
                modifier = Modifier,
                colors = TopAppBarColors(
                    MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = Color.Black,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )
        },
        bottomBar = { BottomAppBar (
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent,
        ) {
            Column (
                modifier = Modifier.fillMaxWidth().fillMaxHeight().align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,  // Add this
                verticalArrangement = Arrangement.Center      // Add this

            ){
                TextButton (
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
            }
        }
        }
    )
    { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            items(5) { index ->
                QuestionBox(index)
            }

        }

    }
}