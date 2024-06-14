package com.yetk.for_student.data.local.repository

import com.yetk.model.Homework
import com.yetk.for_student.data.local.HomeworkDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DefaultHomeworkRepository @Inject constructor(
    private val dao: HomeworkDao
) : HomeworkRepository {


    override suspend fun deleteHomework(homework: Homework) {
        dao.deleteHomework(homework)
    }

    override fun getAll(): Flow<List<Homework>> {
        return dao.getAll()
    }

    override suspend fun updateAll(homeworks: List<Homework>) {
        dao.updateAll(homeworks)
    }

    override suspend fun insertHomework(homework: Homework) {
        dao.insertHomework(homework)
    }

    override suspend fun updateHomework(homework: Homework) {
        dao.updateHomework(homework)
    }

    override fun getHomeworkById(id: Int): Flow<Homework> {
        return dao.getHomeworkById(id)
    }
}