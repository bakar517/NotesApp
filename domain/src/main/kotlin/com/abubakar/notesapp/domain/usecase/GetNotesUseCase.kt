package com.abubakar.notesapp.domain.usecase

import com.abubakar.notesapp.data.repository.NotesRepository
import com.abubakar.notesapp.domain.ext.mapToDomain
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {

    suspend fun getNotes() =
        notesRepository.getNotes().map { notes -> notes.map { it.mapToDomain() } }
}