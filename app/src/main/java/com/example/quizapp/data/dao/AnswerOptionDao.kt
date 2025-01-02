package com.example.quizapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quizapp.data.models.AnswerOptionEntity


@Dao
interface AnswerOptionDao {

    @Query("DELETE FROM answeroptionentity WHERE uid = :answerOptionId")
    suspend fun deleteAnswerOptionById(answerOptionId: Int)

    @Update
    suspend fun updateAnswerOption(answerOptionEntity: AnswerOptionEntity)

    @Insert
    suspend fun insertAnswerOption(vararg answerOptionEntity: AnswerOptionEntity)

    @Query("SELECT * FROM answeroptionentity WHERE uid = :answerOptionId")
    suspend fun getAnswerOptionById(answerOptionId: Int): AnswerOptionEntity

}