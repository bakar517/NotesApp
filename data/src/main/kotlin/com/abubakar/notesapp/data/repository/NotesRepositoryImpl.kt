package com.abubakar.notesapp.data.repository

import com.abubakar.notesapp.data.database.dao.NotesDao
import com.abubakar.notesapp.data.mapper.NoteMapper
import com.abubakar.notesapp.data.model.Note
import com.abubakar.notesapp.data.util.TimeProvider
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val noteMapper: NoteMapper,
    private val timeProvider: TimeProvider,
) : NotesRepository {

    override suspend fun getNotes() =
        notesDao.getNotes().map { noteMapper.mapToNote(it) }

    override suspend fun getNoteDetails(id: Long): Note? =
        notesDao.getNote(id)?.let { noteMapper.mapToNote(it) }

    override suspend fun createNote(title: String, description: String): Note {
        val id = notesDao.insert(noteMapper.createNoteEntity(title, description))

        return requireNotNull(getNoteDetails(id))
    }

    override suspend fun updateNote(id: Long, title: String, description: String): Note {
        val updatedRow = notesDao.update(
            id = id,
            title = title,
            description = description,
            edited = true,
            updatedAt = timeProvider.getCurrentTime(),
        )

        check(updatedRow == 1) { "failed to update note for $id" }

        return requireNotNull(getNoteDetails(id))
    }

    override suspend fun toggleNotePinned(id: Long): Note {
        val entity = notesDao.getNote(id)

        check(entity != null) { "unable to find note for $id" }

        val updatedEntity = entity.copy(pinned = entity.pinned.not())

        notesDao.update(updatedEntity)

        return noteMapper.mapToNote(updatedEntity)
    }

    override suspend fun deleteNote(id: Long): Boolean {
        return notesDao.delete(id) == 1
    }

    override suspend fun deleteNotes(ids: List<Long>) {
        notesDao.delete(ids)
    }
}