package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.example.quizapp.data.models.AnswerOptionEntity


@Dao
interface AnswerOptionDao {
    @Insert
    suspend fun insertAnswerOption(vararg answerOptionEntity: AnswerOptionEntity)
}