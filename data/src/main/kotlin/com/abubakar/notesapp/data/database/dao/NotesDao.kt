package com.abubakar.notesapp.data.database.dao

import androidx.room.*
import com.abubakar.notesapp.data.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: NoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg entities: NoteEntity)

    @Query(value = "SELECT * FROM note")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNote(id: Long): NoteEntity?

    @Query("SELECT * FROM note WHERE title LIKE '%' || :search || '%'")
    fun searchNote(search: String): List<NoteEntity>

    @Query("UPDATE note SET pinned = :pinned WHERE id = :id")
    fun update(id: Long, pinned: Boolean): Int

    @Query("UPDATE note SET title = :title, description = :description, edited = :edited , updated_at = :updatedAt WHERE id = :id")
    fun update(id: Long, title: String, description: String, edited: Boolean, updatedAt: Long): Int

    @Update
    suspend fun update(entity: NoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    fun delete(id: Long): Int

    @Query("DELETE FROM note WHERE id in (:ids)")
    fun delete(ids: List<Long>)
}