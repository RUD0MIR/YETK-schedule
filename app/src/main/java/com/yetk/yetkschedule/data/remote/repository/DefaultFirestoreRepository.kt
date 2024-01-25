package com.yetk.yetkschedule.data.remote.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.yetk.yetkschedule.data.remote.model.BellSchedule
import com.yetk.yetkschedule.data.remote.model.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "DefaultBellScheduleRepo"

class DefaultFirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreRepository {
//    override fun getBellScheduleData(groupId: String): BellSchedule? {
//        var bellSchedule: BellSchedule? = null
//        firestore.collection("bell_schedule").document("Y07qotzCQaQEQY7ROCSB")
////            .whereEqualTo("group", groupId)
//            .get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
////                    bellSchedule = document.documents[0].toObject<BellSchedule>()
//                    bellSchedule = document.toObject<BellSchedule>()
//                    Log.d(TAG, "Success: $bellSchedule")
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//
//
//        Log.d(TAG, "Success actually returns: $bellSchedule")
//        return bellSchedule
//    }

    override fun getBellScheduleData(groupId: String) = callbackFlow<Response<BellSchedule>> {
        val snapshotListener = firestore
            .document("bell_schedule/Y07qotzCQaQEQY7ROCSB")
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val bellSchedule = snapshot.toObject<BellSchedule>()
                    Response.Success(bellSchedule)
                } else {
                    Response.Failure(e)
                }
                trySend(response as Response<BellSchedule>)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getAuthedGroupId(login: String, password: String): String {
        TODO("Not yet implemented")
    }
}