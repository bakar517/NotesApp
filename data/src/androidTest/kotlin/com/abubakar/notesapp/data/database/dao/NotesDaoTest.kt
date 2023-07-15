package com.abubakar.notesapp.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abubakar.notesapp.data.TestDispatcherRule
import com.abubakar.notesapp.data.database.NotesDatabase
import com.abubakar.notesapp.data.database.entity.NoteEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NotesDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var database: NotesDatabase
    private lateinit var notesDao: NotesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()

        notesDao = database.notesDao()
    }

    @Test
    fun get_notes() = runTest {
        val entity1 = sampleEntity.copy(title = "Lorem Ipsum")
        val entity2 = sampleEntity.copy(title = "Lorem Test")

        notesDao.insert(entity1, entity2)

        val actual = notesDao.getNotes().first()

        assertThat(actual).isNotEmpty()
        assertThat(actual[0].title).isEqualTo(entity1.title)
        assertThat(actual[1].title).isEqualTo(entity2.title)
    }

    @Test
    fun search_note() = runTest {
        val entity1 = sampleEntity.copy(title = "Lorem Ipsum")
        val entity2 = sampleEntity.copy(title = "Lorem Test")

        notesDao.insert(entity1, entity2)

        var actual = notesDao.searchNote("Lorem")

        assertThat(actual).isNotEmpty()
        assertThat(actual[0].title).isEqualTo(entity1.title)
        assertThat(actual[1].title).isEqualTo(entity2.title)

        actual = notesDao.searchNote("Lorem I")

        assertThat(actual.size).isEqualTo(1)
        assertThat(actual[0].title).isEqualTo(entity1.title)
    }

    @Test
    fun update_note_note() = runTest {
        var id = notesDao.insert(sampleEntity)

        id = notesDao.update(
            id = id,
            pinned = true
        ).toLong()

        val updatedEntity = notesDao.getNote(id)

        assertThat(updatedEntity).isNotNull()
        assertThat(updatedEntity!!.pinned).isTrue()
    }

    @Test
    fun update_title_description_note() = runTest {
        val newTitle = "updated title"
        val newDescription = "updated description"
        var id = notesDao.insert(sampleEntity)

        id = notesDao.update(
            id = id,
            title = newTitle,
            description = newDescription,
            edited = true,
            updatedAt = 1
        ).toLong()

        val updatedEntity = notesDao.getNote(id)

        assertThat(updatedEntity).isNotNull()
        with(updatedEntity!!) {
            assertThat(title).isEqualTo(newTitle)
            assertThat(description).isEqualTo(newDescription)
            assertThat(edited).isTrue()
            assertThat(updatedAt).isEqualTo(1)
        }
    }

    @Test
    fun update_note() = runTest {
        val id = notesDao.insert(sampleEntity)

        val newNoteEntity = sampleEntity.copy(
            id = id,
            title = "updated title",
            pinned = true,
            edited = true,
        )

        notesDao.update(newNoteEntity)

        assertThat(newNoteEntity).isEqualTo(notesDao.getNote(id))
    }

    @Test
    fun delete_note() = runTest {
        val id = notesDao.insert(sampleEntity)

        val actual = notesDao.delete(id)

        assertThat(actual).isGreaterThan(0)
    }

    @After
    fun tearDown() {
        database.close()
    }


    companion object {
        private val sampleEntity = NoteEntity(
            id = 0,
            title = "title",
            description = "description",
            createdAt = 1,
            updatedAt = 1,
            pinned = false,
            edited = false,
        )
    }
}