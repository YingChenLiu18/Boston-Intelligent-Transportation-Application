package edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payment

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import edu.bu.bostonintelligenttransportationapplication.data.rules.RechargeRecord
import edu.bu.bostonintelligenttransportationapplication.data.rules.Validator
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PaymentViewModel : ViewModel(){
    companion object {
        private const val TAG = "PurchaseActivity"
    }

    var paymentUIState = mutableStateOf(PaymentUIState())

    private var cardValidationsPassed = mutableStateOf(false)

    private var validCreditCard = true

    var paymentInProcess = mutableStateOf(false)

    fun onEvent(event: PaymentUIEvent, navController: NavController){
        when(event) {
            is PaymentUIEvent.CreditCardNumberChanged -> {
                paymentUIState.value = paymentUIState.value.copy(
                    cardNumber = event.cardNumber
                )
            }

            is PaymentUIEvent.CardholderNameChanged -> {
                paymentUIState.value = paymentUIState.value.copy(
                    cardholderName = event.cardholderName
                )
            }

            is PaymentUIEvent.ExpirationMonthChanged -> {
                paymentUIState.value = paymentUIState.value.copy(
                    expirationMonth = event.expirationMonth
                )
            }

            is PaymentUIEvent.ExpirationYearChanged -> {
                paymentUIState.value = paymentUIState.value.copy(
                    expirationYear = event.expirationYear
                )
            }

            is PaymentUIEvent.CvvChanged -> {
                paymentUIState.value = paymentUIState.value.copy(
                    cvv = event.cvv
                )
            }

            is PaymentUIEvent.PaymentAmountChanged ->{
                paymentUIState.value = paymentUIState.value.copy(
                    paymentAmount = event.paymentAmount
                )
            }

            is PaymentUIEvent.ConfirmButtonClick ->{
                validateCreditCardWithRule()
                if(cardValidationsPassed.value) {
                    paymentUIState.value = paymentUIState.value.copy(
                        creditCardIsInvalid = false,
                    )
                    if (validCreditCard) {
                        makeAPayment(paymentUIState.value.paymentAmount)
                        navController.navigate("successfulPayment")
                    } else {
                        paymentUIState.value = paymentUIState.value.copy(
                            creditCardIsInvalid = true
                        )
                    }
                }
            }
        }
    }

    private fun validateCreditCardWithRule(){
        val creditCardNumberResult = Validator.validCreditCard(
            cardNumber = paymentUIState.value.cardNumber
        )

        val cardholderNameResult = Validator.validCardHolder(
            cardholderName = paymentUIState.value.cardholderName
        )

        val cvvResult = Validator.validCvv(
            cvv = paymentUIState.value.cvv
        )

        val expirationDataResult = Validator.validExpirationDate(
            year = paymentUIState.value.expirationYear,
            month = paymentUIState.value.expirationMonth
        )

        paymentUIState.value = paymentUIState.value.copy(
            cardNumberError = !creditCardNumberResult.status,
            cardholderNameError = !cardholderNameResult.status,
            cvvError = !cvvResult.status,
            expirationDataError = !expirationDataResult.status
        )


        cardValidationsPassed.value =
            creditCardNumberResult.status && cardholderNameResult.status && cvvResult.status && expirationDataResult.status
    }

    private fun makeAPayment(sumOfMoney : String){
        when (val money = sumOfMoney.toDouble()) {
            11.00 -> {
                updatePass("Purchase of 1-Day Pass", 1)
            }
            22.50 -> {
                updatePass("Purchase of 7-Day Pass", 7)
            }
            90.00 -> {
                updatePass("Purchase of 31-Day Pass", 31)
            }
            else -> {
                updateBalance("Recharge $${money}", money)
            }
        }
    }

    private fun updateBalance(action : String, money : Double){
        paymentInProcess.value = true

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore
        userId?.let {
            val userDocument = db.collection("users").document(userId)
            userDocument.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val cardInfoData = document.data?.get("TransportationCardInformation") as? Map<String, Any>
                        val currentBalance = cardInfoData?.get("accountBalance") as? Double ?: 0.0

                        val newBalance = currentBalance + money

                        val balanceUpdate =  hashMapOf<String, Any>("TransportationCardInformation.accountBalance" to newBalance)

                        userDocument.update(balanceUpdate)
                            .addOnSuccessListener {
                                Log.d(TAG, "Balance update succeed")
                                updatePurchaseRecord(action)
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Balance update failed", e)
                            }
                        paymentInProcess.value = false
                    } else {
                        Log.d(TAG, "Document does not exist")
                        paymentInProcess.value = false
                    }
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error getting document", e)
                    paymentInProcess.value = false
                }
        } ?: Log.w(TAG, "User ID is null")
    }

    private fun updatePass(action : String, date : Int){
        paymentInProcess.value = true

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore
        userId?.let { uid ->
            val userDocument = db.collection("users").document(uid)
            userDocument.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val cardInfoData = document.data?.get("TransportationCardInformation") as? Map<String, Any>
                        val currentPassExpirationDate = cardInfoData?.get("passExpirationDate") as? String

                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val today = LocalDate.now().minusDays(1)

                        var expirationDate = currentPassExpirationDate?.let { passExpirationDate ->
                            LocalDate.parse(passExpirationDate, formatter).takeIf { date ->
                                date.isAfter(today) || date.isEqual(today)
                            }
                        } ?: today

                        expirationDate = expirationDate.plusDays(date.toLong())

                        val passUpdate = hashMapOf<String, Any>(
                            "TransportationCardInformation.passExpirationDate" to expirationDate.format(formatter)
                        )

                        userDocument.update(passUpdate)
                            .addOnSuccessListener {
                                Log.d(TAG, "Pass expiration date update succeed")
                                updatePurchaseRecord(action)
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Pass expiration date update failed", e)
                            }
                        paymentInProcess.value = false
                    } else {
                        Log.d(TAG, "Document does not exist")
                        paymentInProcess.value = false
                    }
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error getting document", e)
                    paymentInProcess.value = false
                }
        } ?: Log.w(TAG, "User ID is null")
    }

    private fun updatePurchaseRecord(action : String){
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

                    val creditCardAccount = paymentUIState.value.cardNumber

                    val newRecord = RechargeRecord(
                        creditCardAccount = creditCardAccount,
                        rechargeTime = currentTime,
                        rechargeAction = action,
                        currentBalance = String.format("%.2f", currentBalance)
                    )

                    userDocument.update("TransportationCardInformation.accountRechargeRecords", FieldValue.arrayUnion(newRecord))
                        .addOnSuccessListener {
                            Log.d(TAG, "Purchase record update succeed")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Purchase record update failed", e)
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