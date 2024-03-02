package edu.bu.bostonintelligenttransportationapplication.data.account_Data.myProfile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import edu.bu.bostonintelligenttransportationapplication.data.rules.Validator
import edu.bu.bostonintelligenttransportationapplication.screen.main_Modules.logout

class MyProfileViewModel : ViewModel() {
    companion object {
        private const val TAG = "MyProfileActivity"
    }

    var myProfileUIState = mutableStateOf(MyProfileUIState())

    private var allPasswordValidationsPassed = mutableStateOf(false)
    private var allNameValidationsPassed = mutableStateOf(false)

    var myProfileInProcess = mutableStateOf(false)

    fun onEvent(event: MyProfileUIEvent, navController: NavController) {
        when (event) {
            is MyProfileUIEvent.FirstNameChanged -> {
                myProfileUIState.value = myProfileUIState.value.copy(
                    firstName = event.firstName
                )
            }

            is MyProfileUIEvent.LastNameChanged -> {
                myProfileUIState.value = myProfileUIState.value.copy(
                    lastName = event.lastName
                )
            }

            is MyProfileUIEvent.OriginalPasswordChanged -> {
                myProfileUIState.value = myProfileUIState.value.copy(
                    originalPassword = event.originalPassword
                )
            }

            is MyProfileUIEvent.NewPasswordChanged -> {
                myProfileUIState.value = myProfileUIState.value.copy(
                    newPassword = event.newPassword
                )
            }

            is MyProfileUIEvent.NewConfirmPasswordChanged -> {
                myProfileUIState.value = myProfileUIState.value.copy(
                    newConfirmPassword = event.newConfirmPassword
                )
            }

            is MyProfileUIEvent.RestPasswordButtonClick -> {
                validatePasswordDataWithRule()
                if (allPasswordValidationsPassed.value) {
                    restPassword(navController)
                }
            }

            is MyProfileUIEvent.EditNameButtonClick -> {
                validateNameDataWithRule()
                if (allNameValidationsPassed.value) {
                    editName(navController)
                }
            }

        }
    }

    private fun validatePasswordDataWithRule() {

        val passwordResult = Validator.validPassword(
            password = myProfileUIState.value.originalPassword
        )

        val newPasswordResult = Validator.validPassword(
            password = myProfileUIState.value.newPassword
        )

        val newConfirmPasswordResult = Validator.validConfirmPassword(
            password = myProfileUIState.value.newPassword,
            confirmPassword = myProfileUIState.value.newConfirmPassword
        )

        myProfileUIState.value = myProfileUIState.value.copy(
            originalPasswordError = !passwordResult.status,
            newPasswordError = !newPasswordResult.status,
            newConfirmPasswordError = !newConfirmPasswordResult.status
        )

        allPasswordValidationsPassed.value =
            passwordResult.status && newPasswordResult.status && newConfirmPasswordResult.status
    }

    private fun validateNameDataWithRule() {

        val firstNameResult = Validator.validFirstName(
            firstName = myProfileUIState.value.firstName
        )

        val lastNameResult = Validator.validLastName(
            lastName = myProfileUIState.value.lastName
        )


        myProfileUIState.value = myProfileUIState.value.copy(
            firstNameError = !firstNameResult.status,
            lastNameError = !lastNameResult.status,
        )

        allNameValidationsPassed.value =
            firstNameResult.status && lastNameResult.status
    }

/*****************************************************************************
                    * Use in Rest Password Screen *

    Update the password for the current account
*****************************************************************************/
    private fun restPassword(navController: NavController) {
        myProfileInProcess.value = true

        myProfileUIState.value = myProfileUIState.value.copy(
            originalPasswordIncorrect = false
        )

        val user = FirebaseAuth.getInstance().currentUser
        val emailAddress = user?.email
        val originalPassword = myProfileUIState.value.originalPassword
        val newPassword = myProfileUIState.value.newPassword

        if (user != null && emailAddress != null) {
            val credential = EmailAuthProvider.getCredential(emailAddress, originalPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User verification successful")
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    myProfileInProcess.value = false
                                    logout(navController)
                                    Log.d(TAG, "Password updated successfully")
                                } else {
                                    myProfileInProcess.value = false
                                    Log.w(TAG, "Password update failed")
                                }
                            }
                    } else {
                        myProfileInProcess.value = false
                        myProfileUIState.value = myProfileUIState.value.copy(
                            originalPasswordIncorrect = true
                        )
                        Log.w(TAG, "User verification failed")
                    }
                }
        } else {
            myProfileInProcess.value = false
            Log.w(TAG, "User not logged in")
        }
    }

/*****************************************************************************
                     * Use in Edit Name Screen *

    Update the First and Last Name for the current account
*****************************************************************************/
    private fun editName(navController: NavController) {
        myProfileInProcess.value = true

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore

        val newFirstName = myProfileUIState.value.firstName
        val newLastName = myProfileUIState.value.lastName

        val nameInformation = hashMapOf<String, Any>(
            "PersonalInformation.firstName" to newFirstName,
            "PersonalInformation.lastName" to newLastName
        )

        userId?.let {
            db.collection("users").document(userId)
                .update(nameInformation)
                .addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        myProfileInProcess.value = false
                        Log.d(TAG, "Name updated successfully")
                        navController.navigate("account")
                    }
                    else{
                        myProfileInProcess.value = false
                        Log.w(TAG, "Name update failed")
                    }
                }
        }?: run {
            Log.w(TAG, "User ID is null")
            myProfileInProcess.value = false
        }
    }
}