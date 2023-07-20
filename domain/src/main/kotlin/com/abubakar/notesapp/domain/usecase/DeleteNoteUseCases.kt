package com.abubakar.notesapp.domain.usecase

import com.abubakar.notesapp.data.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCases @Inject constructor(
    private val notesRepository: NotesRepository,
) {

    suspend fun deleteNote(id: Long) = notesRepository.deleteNote(id)

    suspend fun deleteNotes(ids: List<Long>) = notesRepository.deleteNotes(ids)
}