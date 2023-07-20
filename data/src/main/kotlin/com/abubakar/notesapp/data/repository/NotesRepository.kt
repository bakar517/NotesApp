package com.abubakar.notesapp.data.repository

import com.abubakar.notesapp.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun getNotes(): Flow<List<Note>>

    suspend fun getNoteDetails(id: Long): Note?

    suspend fun toggleNotePinned(id: Long): Note

    suspend fun createNote(title: String, description: String): Note

    suspend fun updateNote(id: Long, title: String, description: String): Note

    suspend fun deleteNote(id: Long): Boolean
    suspend fun deleteNotes(ids: List<Long>)
}