package com.example.quizapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.dao.AnswerOptionDao
import com.example.quizapp.data.dao.QuestionDao
import com.example.quizapp.data.dao.QuizDao
import com.example.quizapp.data.database.AppDatabase
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.models.WholeQuestion
import com.example.quizapp.data.models.WholeQuiz
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizDao: QuizDao,
    private val questionDao: QuestionDao,
    private val answerOptionDao: AnswerOptionDao,
): ViewModel() {

    private val _quiz = MutableStateFlow(
        WholeQuiz(null, null)
    )
    val quiz: StateFlow<WholeQuiz> = _quiz

    val abc: StateFlow<String> = MutableStateFlow("babab")

    init {
//        viewModelScope.launch {
//            print("BEGINNNNNNNN")
////            abc.collect { Log.d("StateFlow", "Current value: $it") }
////            populateDatabase()
//        }
        viewModelScope.launch {
//            print("BEGINNNNNNNN")

//            populateDatabase()
//            _quiz.value = quizDao.getWholeQuiz(1)
//            quiz.collect { Log.d("StateFlow", "Current value: $it") }


        }
    }

    private suspend fun populateDatabase() {
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
                QuestionEntity(title = questionText, quizId = quizId.toInt())
            )

            val answerOptions = answers.map { answerText ->
                AnswerOptionEntity(questionId = questionId.toInt(), text = answerText)
            }

            answerOptionDao.insertAnswerOption(*answerOptions.toTypedArray())
        }
    }
}