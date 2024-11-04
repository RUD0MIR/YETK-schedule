package com.yetk.forstudent

import com.yetk.forstudent.domain.model.Homework
import com.yetk.forstudent.domain.repository.HomeworkRepository
import kotlinx.coroutines.flow.flow

class FakeHomeworkRepoImpl : HomeworkRepository {
    override suspend fun insertHomework(homework: Homework) {
    }

    override suspend fun updateHomework(homework: Homework) {
    }

    override suspend fun deleteHomework(homework: Homework) {
    }

    override fun getAll() = flow {
        emit(emptyList<Homework>())
    }
}
