package com.yetk.for_student.instrumented

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.yetk.for_student.data.local.HomeworkDao
import com.yetk.for_student.data.local.HomeworkDatabase
import com.yetk.for_student.data.model.HomeworkEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HomeworkDatabaseTest {
    private lateinit var db: HomeworkDatabase
    private lateinit var dao: HomeworkDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            HomeworkDatabase::class.java
        ).build()
        dao = db.dao
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertAndCheckExists() = runTest {
        val homework = HomeworkEntity(
            id = 9309,
            content = "gloriatur",
            subjectName = "Wendi Duffy",
            isVisible = false
        )

        dao.insertHomework(homework)

        val homeworks = backgroundScope.async(UnconfinedTestDispatcher(testScheduler)) {
            dao.getAll().first()
        }

        assertTrue(homeworks.await().size == 1)
        assertEquals(homework, homeworks.await()[0])
    }

    @Test
    fun insertUpdateAndCheckExists() = runTest {
        val homework = HomeworkEntity(
            id = 6095,
            content = null,
            subjectName = null,
            isVisible = false
        )

        dao.insertHomework(homework)

        dao.updateHomework(homework.copy(content = "updated content"))

        val homeworks = backgroundScope.async {
            dao.getAll().first()
        }


        val updatedHomework = homeworks.await()[0]

        assertTrue(homeworks.await().size == 1)
        assertEquals("updated content", updatedHomework.content)
        assertEquals(homework.subjectName, updatedHomework.subjectName)
        assertEquals(homework.isVisible, updatedHomework.isVisible)
        assertEquals(homework.id, updatedHomework.id)
    }

    @Test
    fun deleteAndCheckExists() = runTest {
        val homework = HomeworkEntity(
            id = 5484,
            content = "content 32984",
            subjectName = "math",
            isVisible = true
        )

        dao.insertHomework(homework)

        dao.deleteHomework(homework)

        val homeworks = backgroundScope.async {
            dao.getAll().first()
        }

        assertTrue(homeworks.await().isEmpty())
    }
}