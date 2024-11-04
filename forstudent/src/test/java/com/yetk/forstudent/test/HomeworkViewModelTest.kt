package com.yetk.forstudent.test

import com.yetk.forstudent.FakeHomeworkRepoImpl
import com.yetk.forstudent.ui.screen.homework.HomeworkViewModel
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
