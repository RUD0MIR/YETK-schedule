package com.yetk.for_student.data.local


import android.util.Log
import com.yetk.for_student.data.toData
import com.yetk.for_student.data.toDomain
import com.yetk.for_student.domain.model.Homework
import com.yetk.for_student.domain.repository.HomeworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultHomeworkRepository @Inject constructor(
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

    override fun getHomeworkById(id: Int): Flow<Homework> {
        return dao.getHomeworkById(id).map { it.toDomain() }
    }
}