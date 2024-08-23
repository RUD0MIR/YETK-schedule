package com.yetk.for_student

import com.yetk.for_student.domain.model.Homework
import com.yetk.for_student.domain.repository.HomeworkRepository
import kotlinx.coroutines.flow.flow

class FakeHomeworkRepoImpl: HomeworkRepository {
    override suspend fun insertHomework(homework: Homework) {
    }

    override suspend fun updateHomework(homework: Homework) {
    }

    override suspend fun deleteHomework(homework: Homework) {
    }

    override fun getAll() = flow {
        emit(emptyList<Homework>())
    }

    override fun getHomeworkById(id: Int) = flow {
        emit(Homework(id = 0, content = "", subjectName = "", isVisible = false))
    }
}