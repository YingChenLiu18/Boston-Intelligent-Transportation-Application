package edu.bu.bostonintelligenttransportationapplication.data.account_Data.transportationCard

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class TransportationCardViewModel : ViewModel() {
    companion object {
        private const val TAG = "CardActivity"
    }

    var transportationCardUIState = mutableStateOf(TransportationCardUIState())

    var transportationCardInProcess = mutableStateOf(false)

/*****************************************************************************
                    * Use in Transportation Card Screen *

    Get theTransportation Card  information for the current account
*****************************************************************************/
    fun getTransportationCardInformation() {
        transportationCardInProcess.value = true

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore
        userId?.let {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val data = document.data
                        val cardInfoMap = data?.get("TransportationCardInformation") as? Map<String, Any>

                        val accountNumber = cardInfoMap?.get("accountNumber") as? String ?: "0000 0000 0000 0000"
                        val accountBalance = cardInfoMap?.get("accountBalance") as? Double ?: 0.00
                        val passExpirationDate = cardInfoMap?.get("passExpirationDate") as? String ?: "Not Valid"

                        transportationCardUIState.value = transportationCardUIState.value.copy(
                            transportationCardID = accountNumber,
                            transportationCardBalance = accountBalance,
                            transportationCardPass = passExpirationDate
                        )
                        transportationCardInProcess.value = false
                    } else {
                        Log.d(TAG, "Can't find Transportation Card Information")
                        transportationCardInProcess.value = false
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error getting Transportation Card information", e)
                    transportationCardInProcess.value = false
                }
        } ?: run {
            Log.w(TAG, "User ID is null")
            transportationCardInProcess.value = false
        }
    }
}