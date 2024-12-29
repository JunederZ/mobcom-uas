package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.quizapp.data.models.QuestionEntity

@Dao
interface QuestionDao {
    @Insert
    suspend fun insertQuestion(vararg questionEntity: QuestionEntity): Int
}