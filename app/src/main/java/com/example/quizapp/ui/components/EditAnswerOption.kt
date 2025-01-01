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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun EditAnswerOption (
    optionText: String = "",
    answerId: Int,
    questionId: Int,
    selected: Boolean,
    onSelect: (Int, Int) -> Unit,
    onChange: (String) -> Unit,
    isCorrect: Boolean? = null,
) {

    val backgroundColor = when {
        isCorrect == true -> MaterialTheme.colorScheme.primaryContainer
        isCorrect == false && selected -> MaterialTheme.colorScheme.errorContainer
        selected -> MaterialTheme.colorScheme.primary.copy(0.3f)
        else -> MaterialTheme.colorScheme.surface
    }

    val border = when {
        selected -> MaterialTheme.colorScheme.inversePrimary
        else -> MaterialTheme.colorScheme.onSurface
    }

    val textColor = when {
        isCorrect == true -> MaterialTheme.colorScheme.onPrimaryContainer
        isCorrect == false -> MaterialTheme.colorScheme.onErrorContainer
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
            .clickable { onSelect(questionId, answerId) }
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = optionText,
            onValueChange = onChange,
            textStyle = TextStyle(color = textColor)
        )
    }

}