package com.abubakar.notesapp.data.di

import com.abubakar.notesapp.data.repository.NotesRepository
import com.abubakar.notesapp.data.repository.NotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface RepositoryModule {

    @Binds
    fun bindsNotesRepository(impl: NotesRepositoryImpl): NotesRepository
}