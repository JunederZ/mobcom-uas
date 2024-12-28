package com.example.quizapp.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("questionId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class AnswerOption (
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    val questionId: Int,
    val text: String
)