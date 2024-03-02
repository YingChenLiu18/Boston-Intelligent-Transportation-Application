package edu.bu.bostonintelligenttransportationapplication.data.account_Data.purchaseHistory

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import edu.bu.bostonintelligenttransportationapplication.data.rules.RechargeRecord


class PurchaseHistoryViewModel : ViewModel(){
    companion object {
        private const val TAG = "PurchaseHistoryActivity"
    }

    var purchaseHistoryUIState = mutableStateOf(PurchaseHistoryUIState())

    var purchaseHistoryInProcess = mutableStateOf(false)

/*****************************************************************************
                        * Use in Purchase History Screen *

    Get the purchase history for the current account
 *****************************************************************************/
    fun getPurchaseHistory() {
        purchaseHistoryInProcess.value = true
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore

        userId?.let { uid ->
            val userDocument = db.collection("users").document(uid)
            userDocument.get()
                .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    Log.d(TAG, "Document exist")
                    val cardInfoData = document.data?.get("TransportationCardInformation") as? Map<String, Any>
                    val recordsData = cardInfoData?.get("accountRechargeRecords") as? List<Map<String, Any>>

                    val records = recordsData?.mapNotNull { recordMap ->
                        convertMapToRechargeRecord(recordMap)
                    } ?: listOf()

                    purchaseHistoryUIState.value = purchaseHistoryUIState.value.copy(
                        purchaseHistory = records
                    )
                    purchaseHistoryInProcess.value = false
                }
                else {
                    Log.w(TAG, "Document does not exist")
                    purchaseHistoryInProcess.value = false
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting document: ", exception)
                purchaseHistoryInProcess.value = false
            }
        } ?: run {
            Log.w(TAG, "User ID is null")
            purchaseHistoryInProcess.value = false
        }
    }

    private fun convertMapToRechargeRecord(data: Map<String, Any>): RechargeRecord? {
        return try {
            RechargeRecord(
                creditCardAccount = data["creditCardAccount"] as? String ?: "",
                rechargeTime = data["rechargeTime"] as? String ?: "",
                rechargeAction = data["rechargeAction"] as? String ?: "",
                currentBalance = data["currentBalance"] as? String ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }


}