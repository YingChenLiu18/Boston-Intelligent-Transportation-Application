package edu.bu.bostonintelligenttransportationapplication.data.account_Data.rideHistory

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import edu.bu.bostonintelligenttransportationapplication.data.rules.SpendingRecord


class RideHistoryViewModel : ViewModel(){
    companion object {
        private const val TAG = "RideHistoryActivity"
    }

    var rideHistoryUIState = mutableStateOf(RideHistoryUIState())

    var rideHistoryInProcess = mutableStateOf(false)

/*****************************************************************************
                    * Use in Ride History Screen *

    Get the ride history for the current account
*****************************************************************************/
    fun getRideHistory() {
        rideHistoryInProcess.value = true

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore

        userId?.let { uid ->
            val userDocument = db.collection("users").document(uid)
            userDocument.get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val cardInfoData = document.data?.get("TransportationCardInformation") as? Map<String, Any>
                    val recordsData = cardInfoData?.get("accountSpendingRecords") as? List<Map<String, Any>>

                    val records = recordsData?.mapNotNull { recordMap ->
                        convertMapToSpendingRecord(recordMap)
                    } ?: listOf()

                    rideHistoryUIState.value = rideHistoryUIState.value.copy(
                        rideHistory = records
                    )

                    rideHistoryInProcess.value = false
                }
                else {
                    Log.w(TAG, "Document does not exist")
                    rideHistoryInProcess.value = false
                }
            }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting document: ", exception)
                rideHistoryInProcess.value = false
            }
        }?: run {
            Log.w(TAG, "User ID is null")
            rideHistoryInProcess.value = false
        }
    }

    private fun convertMapToSpendingRecord(data: Map<String, Any>): SpendingRecord? {
        return try {
            SpendingRecord(
                ridingTime = data["ridingTime"] as? String ?: "",
                deductionAction = data["deductionAction"] as? String ?: "",
                currentBalance = data["currentBalance"] as? String ?: "",
            )
        } catch (e: Exception) {
            null
        }
    }


}