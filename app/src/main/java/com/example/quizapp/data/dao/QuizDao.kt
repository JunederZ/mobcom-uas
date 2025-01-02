package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.models.WholeQuiz

@Dao
interface QuizDao {

    @Query("DELETE FROM QuizEntity WHERE uid = :quizId")
    suspend fun deleteQuizById(quizId: Int)

    @Update
    suspend fun updateQuiz(quizEntity: QuizEntity)

    @Query("SELECT * FROM QuizEntity WHERE uid = :quizId")
    suspend fun getQuizById(quizId: Int): QuizEntity

    @Query("SELECT * FROM QuizEntity")
    suspend fun getAllQuiz(): List<QuizEntity>

    @Insert
    suspend fun insertQuiz(quizEntity: QuizEntity): Long

    @Transaction
    @Query("SELECT * FROM QuizEntity WHERE uid = :quizId")
    suspend fun getWholeQuiz(quizId: Int): WholeQuiz


}