package com.yetk.forstudent.data.remote.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.yetk.forstudent.common.Response
import com.yetk.forstudent.data.model.BellScheduleDto
import com.yetk.forstudent.data.model.CollegeGroupDto
import com.yetk.forstudent.data.toDomain
import com.yetk.forstudent.domain.repository.CollegeGroupDataRepository
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

private const val TAG = "DefaultBellScheduleRepo"

class CollegeGroupDataRepoImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore
) : CollegeGroupDataRepository {
    override fun getCollegeGroupData(groupId: String) = callbackFlow {
        val snapshotListener =
            firestore
                .document("CollegeGroup/$groupId")
                .addSnapshotListener { snapshot, e ->
                    val response =
                        if (snapshot != null) {
                            val collegeGroupData = snapshot.toObject<CollegeGroupDto>()
                            if (collegeGroupData != null) {
                                Response.Success(collegeGroupData.toDomain())
                            } else {
                                Response.Failure(e)
                            }
                        } else {
                            Response.Failure(e)
                        }
                    trySend(response)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getBellSchedule() = callbackFlow {
        val snapshotListener =
            firestore
                .document("bell_schedule/Y07qotzCQaQEQY7ROCSB")
                .addSnapshotListener { snapshot, e ->
                    val response =
                        if (snapshot != null) {
                            val bellSchedule = snapshot.toObject<BellScheduleDto>()
                            if (bellSchedule != null) {
                                Response.Success(bellSchedule.toDomain())
                            } else {
                                Response.Failure(e)
                            }
                        } else {
                            Response.Failure(e)
                        }
                    trySend(response)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun isLowerWeek() = callbackFlow {
        val snapshotListener =
            firestore
                .document("weekState/x0n1HLLyjHwNQsSWugj7")
                .addSnapshotListener { snapshot, e ->
                    val response =
                        if (snapshot != null) {
                            val bellSchedule = snapshot["isLowerWeek"]
                            if (bellSchedule != null) {
                                Response.Success(bellSchedule as Boolean)
                            } else {
                                Response.Failure(e)
                            }
                        } else {
                            Response.Failure(e)
                        }
                    trySend(response)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }
}
