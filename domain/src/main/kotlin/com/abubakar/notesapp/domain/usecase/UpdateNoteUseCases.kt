package com.abubakar.notesapp.domain.usecase

import com.abubakar.notesapp.data.repository.NotesRepository
import com.abubakar.notesapp.domain.ext.mapToDomain
import javax.inject.Inject

class UpdateNoteUseCases @Inject constructor(
    private val notesRepository: NotesRepository,
) {

    suspend fun toggleNotePinned(id: Long) = notesRepository.toggleNotePinned(id).mapToDomain()

    suspend fun updateNote(
        id: Long,
        title: String,
        description: String
    ) = notesRepository.updateNote(id, title, description).mapToDomain()

    suspend fun deleteNote(id: Long) = notesRepository.deleteNote(id)
}