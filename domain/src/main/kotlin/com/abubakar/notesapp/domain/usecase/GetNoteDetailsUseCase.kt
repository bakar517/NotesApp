package com.abubakar.notesapp.domain.usecase

import com.abubakar.notesapp.data.repository.NotesRepository
import com.abubakar.notesapp.domain.ext.mapToDomain
import com.abubakar.notesapp.domain.model.Note
import kotlinx.coroutines.flow.map
import javax.inject.Inject

import com.abubakar.notesapp.data.model.Note as NoteModel

class GetNoteDetailsUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {

    suspend fun getNoteDetails(id: Long) = notesRepository.getNoteDetails(id)?.mapToDomain()
}