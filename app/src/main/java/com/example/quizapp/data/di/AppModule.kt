package com.example.quizapp.data.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.quizapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext app: Context)
            = databaseBuilder(app, AppDatabase::class.java, "db").fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideQuizDao(db: AppDatabase) = db.getQuizDao()

    @Singleton
    @Provides
    fun provideQuestionDao(db: AppDatabase) = db.getQuestionDao()

    @Singleton
    @Provides
    fun provideAnswerOptionDao(db: AppDatabase) = db.getAnswerOptionDao()
}