package com.abubakar.notesapp.domain.model

data class Note(
    val id: Long,
    val title: String,
    val description: String,
    val updatedAt: Long,
    val pinned: Boolean,
    val edited: Boolean,
)
