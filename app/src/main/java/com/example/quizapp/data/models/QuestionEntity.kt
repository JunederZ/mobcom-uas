package com.example.quizapp.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = QuizEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("quizId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class QuestionEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    val title: String,
    val quizId: Int
)