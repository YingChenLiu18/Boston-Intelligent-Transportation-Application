package edu.bu.bostonintelligenttransportationapplication.data.account_Data.forgotPassword

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import edu.bu.bostonintelligenttransportationapplication.data.rules.Validator

class ForgotPasswordViewModel : ViewModel() {

    companion object {
        private const val TAG = "ForgotPasswordActivity"
    }

    var forgotPasswordUIState = mutableStateOf(ForgotPasswordUIState())

    private var allValidationsPassed = mutableStateOf(false)

    var forgotPasswordInProcess = mutableStateOf(false)

    fun onEvent(event : ForgotPasswordUIEvent, navController: NavController){
        when(event) {
            is ForgotPasswordUIEvent.EmailAddressEntered -> {
                forgotPasswordUIState.value = forgotPasswordUIState.value.copy(
                    emailAddress = event.emailAddress
                )
            }

            is ForgotPasswordUIEvent.ResetButtonClick -> {
                checkEmailInFireBase(forgotPasswordUIState.value.emailAddress) {success ->
                    if (success) {
                        validateDataWithRule()
                        if (allValidationsPassed.value) {
                            sendResetEmail()
                            navController.navigate("emailSent")
                        }
                    }
                }
            }

        }
    }

    private fun validateDataWithRule(){

        val emailAddressResult = Validator.validEmailAddress(
            emailAddress = forgotPasswordUIState.value.emailAddress
        )

        forgotPasswordUIState.value = forgotPasswordUIState.value.copy(
            emailAddressError = !emailAddressResult.status,
        )

        allValidationsPassed.value = emailAddressResult.status
    }


/*****************************************************************************
                * Use in Forgot Password Screen *

    Send password reset email
*****************************************************************************/
    private fun sendResetEmail(){
        forgotPasswordInProcess.value = true

        FirebaseAuth
            .getInstance()
            .sendPasswordResetEmail(forgotPasswordUIState.value.emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    forgotPasswordInProcess.value = false
                } else {
                    Log.w(TAG, "Error sending email.", task.exception)
                    forgotPasswordInProcess.value = false
                }
            }
    }

/*****************************************************************************
                    * Use in Forgot Password Screen *

    Detect whether the e-mail address is already registered
*****************************************************************************/
    private fun checkEmailInFireBase(email: String, callBack: (Boolean) -> Unit) {
        forgotPasswordUIState.value = forgotPasswordUIState.value.copy(
            emailAddressIsNotUsed = false
        )

        FirebaseAuth
            .getInstance()
            .fetchSignInMethodsForEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val signInMethods = it.result?.signInMethods
                    Log.d(TAG, "$signInMethods")
                    if (signInMethods?.isEmpty() == true) {
                        forgotPasswordUIState.value = forgotPasswordUIState.value.copy(
                            emailAddressIsNotUsed = true
                        )
                        Log.d(TAG, "Email is not registered")
                        callBack(false)
                    } else {
                        forgotPasswordUIState.value = forgotPasswordUIState.value.copy(
                            emailAddressIsNotUsed = false
                        )
                        Log.d(TAG, "Email is registered")
                        callBack(true)
                    }
                } else {
                    Log.w(TAG, "Check Email Error")
                    callBack(false)
                }
            }
    }
}