package com.example.quizapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quiz (
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    var title: String,

)