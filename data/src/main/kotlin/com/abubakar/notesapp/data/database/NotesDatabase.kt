package com.abubakar.notesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abubakar.notesapp.data.database.dao.NotesDao
import com.abubakar.notesapp.data.database.entity.NoteEntity

@Database(
    entities = [
        NoteEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
internal abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}
