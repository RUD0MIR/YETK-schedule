package com.yetk.for_student.test

import com.yetk.for_student.FakeHomeworkRepoImpl
import com.yetk.for_student.ui.screen.homework.HomeworkViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeworkViewModelTest {
    @Test
    fun `checks input correctly`() {
        val vm = HomeworkViewModel(FakeHomeworkRepoImpl())
        val checkInput = vm::checkCorrectInput

        assertFalse(checkInput("", ""))
        assertFalse(checkInput(" ", " "))

        assertTrue(checkInput("ff", ""))
        assertTrue(checkInput("", "ff"))
        assertTrue(checkInput("fff", "fff"))
    }

}