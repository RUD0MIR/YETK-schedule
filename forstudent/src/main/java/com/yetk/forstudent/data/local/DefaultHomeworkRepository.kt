package com.yetk.forstudent.data.local

import com.yetk.forstudent.data.toData
import com.yetk.forstudent.data.toDomain
import com.yetk.forstudent.domain.model.Homework
import com.yetk.forstudent.domain.repository.HomeworkRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultHomeworkRepository
@Inject
constructor(
    private val dao: HomeworkDao
) : HomeworkRepository {
    override suspend fun deleteHomework(homework: Homework) {
        dao.deleteHomework(homework.toData())
    }

    override fun getAll(): Flow<List<Homework>> {
        return dao.getAll().map { homeworks -> homeworks.map { it.toDomain() } }
    }

    override suspend fun insertHomework(homework: Homework) {
        dao.insertHomework(homework.toData())
    }

    override suspend fun updateHomework(homework: Homework) {
        dao.updateHomework(homework.toData())
    }
}
