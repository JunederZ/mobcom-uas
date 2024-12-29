package com.example.quizapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.quizapp.data.dao.AnswerOptionDao
import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.data.dao.QuizDao
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.ui.navigation.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [QuizEntity::class, QuestionEntity::class, AnswerOptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getQuizDao(): QuizDao
    abstract fun getQuestionDao(): QuestionDao
    abstract fun getAnswerOptionDao(): AnswerOptionDao

//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "quiz_database"
//                )
//                    .addCallback(object : Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            // Prepopulate database if needed
//                            CoroutineScope(Dispatchers.IO).launch {
//                                val database = getDatabase(context)
//                                populateDatabase(
//                                    database.quizDao(),
//                                    database.questionDao(),
//                                    database.answerOptionDao()
//                                )
//                            }
//                        }
//                    })
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }


}
