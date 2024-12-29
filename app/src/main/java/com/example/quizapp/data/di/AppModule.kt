package com.example.quizapp.data.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.quizapp.data.database.AppDatabase
//import com.example.quizapp.data.database.populateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext app: Context)
            = databaseBuilder(app, AppDatabase::class.java, "db")
//                .addCallback(object : RoomDatabase.Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        val database = databaseBuilder(
//                            app,
//                            AppDatabase::class.java,
//                            "db"
//                        ).build()
//
//                        CoroutineScope(Dispatchers.IO).launch {
//                            populateDatabase(database)
//                        }
//                    }
//                })
                .fallbackToDestructiveMigration()
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