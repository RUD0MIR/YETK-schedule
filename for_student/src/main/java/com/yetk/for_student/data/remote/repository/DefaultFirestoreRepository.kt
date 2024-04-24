package com.yetk.for_student.data.remote.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.yetk.model.BellSchedule
import com.yetk.model.CollegeGroup
import com.yetk.model.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "DefaultBellScheduleRepo"

class DefaultFirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreRepository {
    override fun getCollegeGroupData(groupId: String) = callbackFlow {
        val snapshotListener = firestore
            .document("CollegeGroup/$groupId")
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val collegeGroupData = snapshot.toObject<CollegeGroup>()
                    if(collegeGroupData != null) {
                        Response.Success(collegeGroupData)
                    }
                    else{
                        Response.Failure(e)
                    }
                } else {
                    Response.Failure(e)
                }
                trySend(response as Response<CollegeGroup>)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }


    override fun getBellSchedule() = callbackFlow {
        val snapshotListener = firestore
            .document("bell_schedule/Y07qotzCQaQEQY7ROCSB")
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val bellSchedule = snapshot.toObject<BellSchedule>()
                    if(bellSchedule != null) {
                        Response.Success(bellSchedule)
                    }
                    else {
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
        val snapshotListener = firestore
            .document("weekState/x0n1HLLyjHwNQsSWugj7")
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val bellSchedule = snapshot["isLowerWeek"]
                    if(bellSchedule != null) {
                        Response.Success(bellSchedule)
                    } else {
                        Response.Failure(e)
                    }
                } else {
                    Response.Failure(e)
                }
                trySend(response as Response<Boolean>)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }
}
