package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.models.WholeQuiz

@Dao
interface QuizDao {

    @Insert
    suspend fun insertQuiz(quizEntity: QuizEntity): Int

    @Transaction
    @Query("SELECT * FROM QuizEntity WHERE uid = :quizId")
    suspend fun getWholeQuiz(quizId: Int): WholeQuiz
}