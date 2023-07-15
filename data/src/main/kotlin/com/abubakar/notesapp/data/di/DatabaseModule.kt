package com.abubakar.notesapp.data.di

import android.content.Context
import androidx.room.Room
import com.abubakar.notesapp.data.database.NotesDatabase
import com.abubakar.notesapp.data.database.dao.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DatabaseModule {

    @Provides
    fun providesNotesDao(
        database: NotesDatabase,
    ): NotesDao = database.notesDao()

    @Provides
    @Singleton
    fun providesNotesDatabase(
        @ApplicationContext context: Context,
    ): NotesDatabase = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        DB_NAME,
    ).build()


    companion object {
        private const val DB_NAME = "notes-database"
    }
}