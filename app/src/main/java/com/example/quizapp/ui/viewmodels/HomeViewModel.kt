package com.example.quizapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.models.AnswerOptionEntity
import com.example.quizapp.data.models.QuestionEntity
import com.example.quizapp.data.models.QuizEntity
import com.example.quizapp.data.repositories.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _quizList = MutableStateFlow<List<QuizEntity>>(listOf())
    val quizList: StateFlow<List<QuizEntity>> = _quizList

    private val _navigationEvents = MutableStateFlow<String?>(null)
    val navigationEvents = _navigationEvents.asStateFlow()

    fun navigateToEditQuiz(quizId: Int) {
        _navigationEvents.value = "editQuiz/$quizId"
    }

    fun navigateToQuiz(quizId: Int) {
        viewModelScope.launch {
            _navigationEvents.value = "quiz/$quizId"
        }
    }

    fun onNavigated() {
        _navigationEvents.value = null
    }

    init {
        viewModelScope.launch {
            _quizList.value = quizRepository.getAllQuiz()
        }
    }

    fun populateDatabase() {
        viewModelScope.launch {
            val quiz = QuizEntity(title = "General Knowledge")
            val quizId = quizRepository.insertQuiz(quiz)

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
                val questionId = quizRepository.insertQuestion(
                    QuestionEntity(title = questionText, quizId = quizId.toInt())
                )

                val isCorrect = Random.nextInt(0, 3)
                val answerOptions = answers.mapIndexed { index, answerText ->
                    if (isCorrect == index) {
                        AnswerOptionEntity(
                            questionId = questionId.toInt(),
                            text = answerText,
                            correct = true
                        )
                    } else {
                        AnswerOptionEntity(questionId = questionId.toInt(), text = answerText)
                    }
                }

                quizRepository.insertAnswerOption(*answerOptions.toTypedArray())
            }
        }
    }

}