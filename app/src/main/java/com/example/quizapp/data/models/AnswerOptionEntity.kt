package com.example.quizapp.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = QuestionEntity::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("questionId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class AnswerOptionEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    val questionId: Int,
    val text: String
)