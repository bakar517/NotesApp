package com.abubakar.notesapp.data.mapper

import com.abubakar.notesapp.data.database.entity.NoteEntity
import com.abubakar.notesapp.data.model.Note
import com.abubakar.notesapp.data.util.TimeProvider
import javax.inject.Inject

internal class NoteMapper @Inject constructor(
    private val timeProvider: TimeProvider,
) {

    fun mapToNote(entities: List<NoteEntity>) = entities.map { mapToNote(it) }

    fun mapToNote(entity: NoteEntity) = with(entity) {
        Note(
            id = id,
            title = title,
            description = description,
            updatedAt = updatedAt,
            pinned = pinned,
            edited = edited,
        )
    }

    fun createNoteEntity(title: String, description: String) = NoteEntity(
        id = 0,
        title = title,
        description = description,
        createdAt = timeProvider.getCurrentTime(),
        updatedAt = timeProvider.getCurrentTime(),
        edited = false,
        pinned = false,
    )

}