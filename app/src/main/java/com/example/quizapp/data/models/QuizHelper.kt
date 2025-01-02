package com.example.quizapp.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class WholeQuiz(
    @Embedded val quiz: QuizEntity?,
    @Relation(
        parentColumn = "uid",
        entityColumn = "quizId",
        entity = QuestionEntity::class
    )
    val questions: List<WholeQuestion>?
)

data class WholeQuestion(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "uid",
        entityColumn = "questionId"
    )
    val answerOptions: List<AnswerOptionEntity>
)
