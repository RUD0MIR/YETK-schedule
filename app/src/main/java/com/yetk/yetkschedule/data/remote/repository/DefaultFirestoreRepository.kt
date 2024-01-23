package com.yetk.yetkschedule.data.remote.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.yetk.yetkschedule.data.remote.model.BellSchedule
import javax.inject.Inject

private const val TAG = "DefaultBellScheduleRepo"
class DefaultFireStoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): FirestoreRepository {
    override fun getBellScheduleData(groupId: String): BellSchedule? {
        var bellSchedule: BellSchedule? = null
        firestore.collection("bell_schedule")
            .whereEqualTo("group", groupId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    bellSchedule = document.documents[0].toObject<BellSchedule>()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return bellSchedule
    }

    override fun getAuthedGroupId(login: String, password: String): String {
        TODO("Not yet implemented")
    }
}