package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.models.WholeQuiz

@Dao
interface QuestionDao {

    // get quiz from questionId
    @Query("SELECT * FROM QuizEntity WHERE uid = (SELECT quizId FROM QuestionEntity WHERE uid = :questionId)")
    suspend fun getQuizByQuestionId(questionId: Int): WholeQuiz


    @Query("SELECT * FROM QuestionEntity WHERE quizId = :quizId")
    suspend fun getQuestionsByQuizId(quizId: Int): List<QuestionEntity>

    @Query("SELECT * FROM QuestionEntity WHERE uid = :questionId")
    suspend fun getQuestionById(questionId: Int): QuestionEntity

    @Query("SELECT * FROM AnswerOptionEntity WHERE questionId = :questionId")
    suspend fun getAnswerOptionsByQuestionId(questionId: Int): List<AnswerOptionEntity>

    @Insert
    suspend fun insertQuestion(questionEntity: QuestionEntity): Long
}