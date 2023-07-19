package com.abubakar.notesapp.domain.ext

import com.abubakar.notesapp.domain.model.Note
import com.abubakar.notesapp.data.model.Note as NoteModel

internal fun NoteModel.mapToDomain() = Note(
    id = id,
    title = title,
    description = description,
    updatedAt = updatedAt,
    pinned = pinned,
    edited = edited
)
