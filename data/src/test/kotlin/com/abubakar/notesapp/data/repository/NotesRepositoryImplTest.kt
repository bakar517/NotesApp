package com.abubakar.notesapp.data.repository

import com.abubakar.notesapp.data.TestDispatcherRule
import com.abubakar.notesapp.data.database.dao.NotesDao
import com.abubakar.notesapp.data.database.entity.NoteEntity
import com.abubakar.notesapp.data.mapper.NoteMapper
import com.abubakar.notesapp.data.model.Note
import com.abubakar.notesapp.data.util.TimeProvider
import com.google.common.truth.Truth.*
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class NotesRepositoryImplTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    @MockK
    lateinit var notesDao: NotesDao

    @MockK
    lateinit var noteMapper: NoteMapper

    @MockK
    lateinit var timeProvider: TimeProvider

    @InjectMockKs
    lateinit var notesRepositoryImpl: NotesRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun `get notes`() = runTest {
        coEvery { notesDao.getNotes() }.returns(flowOf(listOf(sampleEntity, sampleEntity)))
        every { noteMapper.mapToNote(any<List<NoteEntity>>()) }.returns(
            listOf(
                sampleNote,
                sampleNote
            )
        )

        val actual = notesRepositoryImpl.getNotes().first()

        assertThat(actual).isEqualTo(listOf(sampleNote, sampleNote))

    }

    @Test
    fun `get note details`() = runTest {
        val expected = sampleNote

        coEvery { notesDao.getNote(any()) }.returns(sampleEntity)
        every { noteMapper.mapToNote(sampleEntity) }.returns(sampleNote)

        val actual = notesRepositoryImpl.getNoteDetails(1)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `toggle pinned note`() = runTest {
        val noteEntity = sampleEntity.copy(pinned = false)
        val updatedEntity = sampleEntity.copy(pinned = true)

        coEvery { notesDao.update(any(), any()) }.returns(1)
        coEvery { notesDao.getNote(any()) }.returns(noteEntity)
        every { noteMapper.mapToNote(updatedEntity) }.returns(sampleNote)

        val actual = notesRepositoryImpl.toggleNotePinned(1)

        assertThat(actual).isEqualTo(sampleNote)

        coVerify { notesDao.update(updatedEntity) }
    }

    @Test
    fun `create note`() = runTest {
        val title = "title"
        val description = "description"
        every { noteMapper.createNoteEntity(title, description) }.returns(sampleEntity)
        coEvery { notesDao.insert(sampleEntity) }.returns(1)
        coEvery { notesDao.getNote(1) }.returns(sampleEntity)
        every { noteMapper.mapToNote(sampleEntity) }.returns(sampleNote)

        val actual = notesRepositoryImpl.createNote(title, description)

        assertThat(actual).isEqualTo(sampleNote)

        coVerify { notesDao.insert(any()) }

    }

    @Test
    fun `update note`() = runTest {
        val expected = sampleNote

        coEvery { notesDao.update(any(), any(), any(), any(), any()) }.returns(1)
        coEvery { notesDao.getNote(any()) }.returns(sampleEntity)
        every { noteMapper.mapToNote(sampleEntity) }.returns(sampleNote)

        val actual = notesRepositoryImpl.updateNote(1, "", "")

        assertThat(actual).isEqualTo(expected)

        verify { notesDao.update(any(), any(), any(), any(), any()) }
        verify { timeProvider.getCurrentTime() }
    }

    @Test
    fun `delete note`() = runTest {
        every { notesDao.delete(1) }.returns(1)

        val actual = notesRepositoryImpl.deleteNote(1)

        assertThat(actual).isTrue()
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

        private val sampleNote = Note(
            id = 0,
            title = "title",
            description = "description",
            updatedAt = 1,
            pinned = false,
            edited = false,
        )

    }
}