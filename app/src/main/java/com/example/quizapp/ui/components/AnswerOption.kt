package com.example.quizapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.theme.ExtendedColorScheme
import com.example.quizapp.ui.theme.extendedDark
import com.example.quizapp.ui.theme.onContainerSuccess
import com.example.quizapp.ui.theme.success
import com.example.quizapp.ui.theme.successContainer
import com.example.quizapp.ui.viewmodels.QuizViewModel


@Composable
fun AnswerOption (
    optionText: String = "",
    answerId: Int,
    questionId: Int,
    selected: Boolean,
    onSelect: (Int, Int, Int) -> Unit,
    isCorrect: Boolean? = null,
    index: Int,
    viewModel: QuizViewModel = hiltViewModel()
) {

    val isQuizComplete by viewModel.isQuizComplete.collectAsState()



    val backgroundColor = when {
        isCorrect == true -> MaterialTheme.colorScheme.successContainer
        isCorrect == false && selected -> MaterialTheme.colorScheme.errorContainer
        selected -> MaterialTheme.colorScheme.primary.copy(0.3f)
        else -> MaterialTheme.colorScheme.surface
    }

    val border = when {
        isCorrect == true -> MaterialTheme.colorScheme.success
        isCorrect == false && selected -> MaterialTheme.colorScheme.error
        selected -> MaterialTheme.colorScheme.inversePrimary
        else -> MaterialTheme.colorScheme.onSurface
    }

    val textColor = when {
        isCorrect == true -> MaterialTheme.colorScheme.onContainerSuccess
        isCorrect == false && selected -> MaterialTheme.colorScheme.onErrorContainer
        selected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .border(
                BorderStroke(2.dp, border),
                MaterialTheme.shapes.large
            )
            .background(backgroundColor)
            .fillMaxWidth()
            .clickable {
                if (!isQuizComplete) onSelect(index, questionId, answerId)
            }
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(optionText, color = textColor )
    }

}