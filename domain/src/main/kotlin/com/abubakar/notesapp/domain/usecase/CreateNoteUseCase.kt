package com.abubakar.notesapp.domain.usecase

import com.abubakar.notesapp.data.repository.NotesRepository
import com.abubakar.notesapp.domain.ext.mapToDomain
import com.abubakar.notesapp.domain.model.Note
import kotlinx.coroutines.flow.map
import javax.inject.Inject

import com.abubakar.notesapp.data.model.Note as NoteModel

class CreateNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {

    suspend fun createNote(title: String, description: String) =
        notesRepository.createNote(title, description).mapToDomain()
}