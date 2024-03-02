package edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payingTheFare

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import edu.bu.bostonintelligenttransportationapplication.data.rules.SpendingRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PayingTheFireViewModel : ViewModel(){
    companion object {
        private const val TAG = "PayingTheFireActivity"
    }

    var payingTheFireInProcess = mutableStateOf(false)

    fun onEvent(event: PayingTheFareUIEvent, navController: NavController){
        when(event) {
            is PayingTheFareUIEvent.TapButtonClick ->{
                payingTheFare{ success ->
                    if (success) {
                        navController.navigate("card")
                    } else {
                        navController.navigate("failureToPay")
                    }
                }
            }
        }
    }

    private fun payingTheFare(callback: (Boolean) -> Unit){
        payingTheFireInProcess.value = true

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore
        userId?.let { uid ->
            val userDocument = db.collection("users").document(uid)
            userDocument.get()
                .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val cardInfoData = document.data?.get("TransportationCardInformation") as? Map<String, Any>
                    val currentBalance = cardInfoData?.get("accountBalance") as? Double ?: 0.0
                    val currentPassExpirationDate = cardInfoData?.get("passExpirationDate") as? String

                    val fare = 2.4

                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val today = LocalDate.now().minusDays(1)

                    val expirationDate = currentPassExpirationDate?.let { passExpirationDate ->
                        LocalDate.parse(passExpirationDate, formatter).takeIf { date ->
                            date.isAfter(today) || date.isEqual(today)
                        }
                    } ?: today

                    if(currentBalance < fare && expirationDate.isBefore(LocalDate.now())){
                        callback(false)
                    } else {
                        val newBalance = if (expirationDate.isBefore(LocalDate.now())) {
                            currentBalance - fare
                        } else {
                            currentBalance
                        }

                        val action = if (expirationDate.isBefore(LocalDate.now())) {
                            "Paying 2.4$"
                        } else {
                            "Free ride"
                        }

                        val balanceUpdate = hashMapOf<String, Any>(
                            "TransportationCardInformation.accountBalance" to newBalance
                        )

                        userDocument.update(balanceUpdate)
                            .addOnSuccessListener {
                                Log.d(TAG, "balance update succeed")
                                updateRidingRecord(action)
                                callback(true)
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "balance update failed", e)
                                callback(false)
                            }
                    }
                    payingTheFireInProcess.value = false
                } else {
                    Log.d(TAG, "Document does not exist")
                    payingTheFireInProcess.value = false
                    callback(false)
                }
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error getting document", e)
                    payingTheFireInProcess.value = false
            }
        } ?: Log.w(TAG, "User ID is null")
    }

    private fun updateRidingRecord(action : String){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore

        userId?.let { uid ->
            val userDocument = db.collection("users").document(uid)

            userDocument.get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val cardInfoData = document.data?.get("TransportationCardInformation") as? Map<String, Any>
                    val currentBalance = cardInfoData?.get("accountBalance") as? Double ?: 0.0

                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val currentTime = LocalDateTime.now().format(formatter)

                    val newRecord = SpendingRecord(
                        ridingTime = currentTime,
                        deductionAction = action,
                        currentBalance = String.format("%.2f", currentBalance)
                    )

                    userDocument.update("TransportationCardInformation.accountSpendingRecords", FieldValue.arrayUnion(newRecord))
                        .addOnSuccessListener {
                            Log.d(TAG, "Spending record update succeed")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Spending record update failed", e)
                        }
                } else {
                    Log.d(TAG, "Document does not exist")
                }
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error getting document", e)
            }
        } ?: Log.w(TAG, "User ID is null")
    }
}