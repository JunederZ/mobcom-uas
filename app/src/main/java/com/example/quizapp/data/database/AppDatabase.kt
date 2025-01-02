package com.example.quizapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizapp.data.dao.AnswerOptionDao
import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.data.dao.QuizDao
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity

@Database(
    entities = [QuizEntity::class, QuestionEntity::class, AnswerOptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getQuizDao(): QuizDao
    abstract fun getQuestionDao(): QuestionDao
    abstract fun getAnswerOptionDao(): AnswerOptionDao


}
