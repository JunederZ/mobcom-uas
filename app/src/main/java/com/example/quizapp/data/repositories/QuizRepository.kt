package com.example.quizapp.data.repositories

import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.data.dao.AnswerOptionDao
import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.data.dao.QuizDao
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.models.WholeQuiz
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizDao: QuizDao,
    private val questionDao: QuestionDao,
    private val answerOptionDao: AnswerOptionDao,
) {
    suspend fun getAllQuiz() = quizDao.getAllQuiz()
    suspend fun insertQuiz(quizEntity: QuizEntity): Long = quizDao.insertQuiz(quizEntity)
    suspend fun getWholeQuiz(quizId: Int): WholeQuiz = quizDao.getWholeQuiz(quizId)

    suspend fun updateQuiz(quizEntity: QuizEntity) = quizDao.updateQuiz(quizEntity)
    suspend fun updateQuestion(questionEntity: QuestionEntity) = questionDao.updateQuestion(questionEntity)
    suspend fun updateAnswerOption(answerOptionEntity: AnswerOptionEntity) = answerOptionDao.updateAnswerOption(answerOptionEntity)

    suspend fun getQuizByQuestionId(questionId: Int): WholeQuiz =
        questionDao.getQuizByQuestionId(questionId)

    suspend fun getQuestionsByQuizId(quizId: Int) = questionDao.getQuestionsByQuizId(quizId)
    suspend fun getQuestionById(questionId: Int): QuestionEntity =
        questionDao.getQuestionById(questionId)

    suspend fun getAnswerOptionsByQuestionId(questionId: Int): List<AnswerOptionEntity> =
        questionDao.getAnswerOptionsByQuestionId(questionId)

    suspend fun insertQuestion(questionEntity: QuestionEntity): Long =
        questionDao.insertQuestion(questionEntity)

    suspend fun insertAnswerOption(vararg answerOptionEntity: AnswerOptionEntity) =
        answerOptionDao.insertAnswerOption(*answerOptionEntity)

}