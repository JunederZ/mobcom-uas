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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [QuizEntity::class, QuestionEntity::class, AnswerOptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerOptionDao(): AnswerOptionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "quiz_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Prepopulate database if needed
                            CoroutineScope(Dispatchers.IO).launch {
                                val database = getDatabase(context)
                                populateDatabase(
                                    database.quizDao(),
                                    database.questionDao(),
                                    database.answerOptionDao()
                                )
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateDatabase(
            quizDao: QuizDao,
            questionDao: QuestionDao,
            answerOptionDao: AnswerOptionDao
        ) {
            val quiz = QuizEntity(title = "General Knowledge Quiz")
            val quizId = quizDao.insertQuiz(quiz)

            val questionsAndAnswers = listOf(
                "What is the capital of France?" to listOf("Paris", "London", "Berlin", "Rome"),
                "What is 2 + 2?" to listOf("3", "4", "5", "6"),
                "What is the largest planet?" to listOf("Mars", "Earth", "Jupiter", "Venus"),
                "Who wrote 'Romeo and Juliet'?" to listOf(
                    "Shakespeare",
                    "Hemingway",
                    "Austen",
                    "Tolstoy"
                ),
                "Which is the longest river in the world?" to listOf(
                    "Amazon",
                    "Nile",
                    "Yangtze",
                    "Mississippi"
                ),
                "What is the smallest prime number?" to listOf("1", "2", "3", "5"),
                "What color is a ripe banana?" to listOf("Red", "Yellow", "Green", "Blue"),
                "What is the square root of 16?" to listOf("2", "4", "8", "16"),
                "What is the chemical symbol for water?" to listOf("H2O", "O2", "CO2", "HO2"),
                "What is the capital of Japan?" to listOf("Seoul", "Tokyo", "Beijing", "Bangkok")
            )

            for ((questionText, answers) in questionsAndAnswers) {
                val questionId = questionDao.insertQuestion(
                    QuestionEntity(title = questionText, quizId = quizId)
                )

                val answerOptions = answers.map { answerText ->
                    AnswerOptionEntity(questionId = questionId, text = answerText)
                }

                answerOptionDao.insertAnswerOption(*answerOptions.toTypedArray())
            }
        }

    }
}