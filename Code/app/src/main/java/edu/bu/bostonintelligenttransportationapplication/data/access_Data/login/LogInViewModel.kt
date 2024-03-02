package edu.bu.bostonintelligenttransportationapplication.data.access_Data.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import edu.bu.bostonintelligenttransportationapplication.data.rules.Validator

class LogInViewModel : ViewModel() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    var loginUIState = mutableStateOf(LogInUIState())

    private var allValidationsPassed = mutableStateOf(false)

    var loginInProcess = mutableStateOf(false)

    fun onEvent(event : LogInUIEvent, navController: NavController){
        when(event) {
            is LogInUIEvent.EmailAddressChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    emailAddress = event.emailAddress
                )
            }

            is LogInUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LogInUIEvent.LogInButtonClick -> {
                validateDataWithRule { valid ->
                    if(valid) {
                        if (allValidationsPassed.value) {
                            signIn(navController)
                            getPersonalInformation{}
                        }
                    }
                }
            }
        }
    }

    private fun validateDataWithRule(callback: (Boolean) -> Unit){

        val emailAddressResult = Validator.validEmailAddress(
            emailAddress = loginUIState.value.emailAddress
        )

        val passwordResult = Validator.validPassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailAddressError = !emailAddressResult.status,
            passwordError = !passwordResult.status,
        )


        allValidationsPassed.value = emailAddressResult.status && passwordResult.status

        if(allValidationsPassed.value){
            callback(true)
        }
        else{
            callback(false)
        }
    }

/*****************************************************************************
                            * Use in Log In Screen *

    Login via user-entered account and password
*****************************************************************************/
    private fun signIn(navController: NavController){
        loginInProcess.value = true

        loginUIState.value = loginUIState.value.copy(
            accountOrPasswordIncorrect = false,
        )

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(loginUIState.value.emailAddress, loginUIState.value.password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    navController.navigate("pay")
                    loginInProcess.value = false
                }
                else{
                    loginInProcess.value = false
                    loginUIState.value = loginUIState.value.copy(
                        accountOrPasswordIncorrect = true
                    )
                }
            }
    }

/*****************************************************************************
                                * Use in Account Screen *

    Get the user's name and email
*****************************************************************************/

    fun getPersonalInformation(callback: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore
        userId?.let {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val data = document.data
                        val personalInfoMap = data?.get("PersonalInformation") as? Map<String, Any>

                        val firstName = personalInfoMap?.get("firstName") as? String ?: ""
                        val lastName = personalInfoMap?.get("lastName") as? String ?: ""
                        val emailAddress = personalInfoMap?.get("emailAddress") as? String ?: ""

                        loginUIState.value = loginUIState.value.copy(
                            firstName = firstName,
                            lastName = lastName,
                            emailAddress = emailAddress
                        )
                        callback(true)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error getting personal information", e)
                    callback(false)
                }
        }?: run {
            Log.w(TAG, "User ID is null")
        }
    }
}