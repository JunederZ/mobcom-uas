package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.data.models.QuestionEntity

@Dao
interface QuestionDao {

    @Query("SELECT * FROM QuestionEntity WHERE quizId = :quizId")
    suspend fun getQuestionsByQuizId(quizId: String): List<QuestionEntity>

    @Query("SELECT * FROM QuestionEntity WHERE uid = :questionId")
    suspend fun getQuestionById(questionId: Int): QuestionEntity

    @Insert
    suspend fun insertQuestion(questionEntity: QuestionEntity): Long
}