package com.example.quizapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuizEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    var title: String,

)