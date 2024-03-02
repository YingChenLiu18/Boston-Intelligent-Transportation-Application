package edu.bu.bostonintelligenttransportationapplication.data.access_Data.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.firestore
import edu.bu.bostonintelligenttransportationapplication.data.rules.TransportationCardInformation
import edu.bu.bostonintelligenttransportationapplication.data.rules.PersonalInformation
import edu.bu.bostonintelligenttransportationapplication.data.rules.Validator
import kotlin.random.Random

class RegisterViewModel : ViewModel(){

    companion object {
        private const val TAG = "RegisterActivity"
    }

    var registerUIState = mutableStateOf(RegisterUIState())

    private var allValidationsPassed = mutableStateOf(false)

    var signUpInProcess = mutableStateOf(false)
    fun onEvent(event: RegisterUIEvent, navController: NavController){
        when(event){
            is RegisterUIEvent.FirstNameChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    firstName = event.firstName
                )
            }

            is RegisterUIEvent.LastNameChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    lastName = event.lastName
                )
            }

            is RegisterUIEvent.EmailAddressChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    emailAddress = event.emailAddress
                )
            }

            is RegisterUIEvent.PasswordChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    password = event.password
                )
            }

            is RegisterUIEvent.ConfirmPasswordChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    confirmPassword = event.confirmPassword
                )
            }

            is RegisterUIEvent.RulesCheckBoxClick -> {
                registerUIState.value = registerUIState.value.copy(
                    clickCheckBoxAccepted = event.status
                )
            }

            is RegisterUIEvent.RegisterButtonClick -> {
                validateDataWithRule{ valid ->
                    if(valid) {
                        checkEmailInFireBase(registerUIState.value.emailAddress) { success ->
                            if (success) {
                                if (allValidationsPassed.value) {
                                    register(navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validateDataWithRule(callBack: (Boolean) -> Unit){
        val firstNameResult = Validator.validFirstName(
            firstName = registerUIState.value.firstName
        )

        val lastNameResult = Validator.validLastName(
            lastName = registerUIState.value.lastName
        )

        val emailAddressResult = Validator.validEmailAddress(
            emailAddress = registerUIState.value.emailAddress
        )

        val passwordResult = Validator.validPassword(
            password = registerUIState.value.password
        )

        val confirmPasswordResult = Validator.validConfirmPassword(
            password = registerUIState.value.password,
            confirmPassword = registerUIState.value.confirmPassword
        )

        val rulesCheckBoxResult = Validator.validRulesCheckBox(
            statusValue = registerUIState.value.clickCheckBoxAccepted
        )

        registerUIState.value = registerUIState.value.copy(
            firstNameError = !firstNameResult.status,
            lastNameError = !lastNameResult.status,
            emailAddressError = !emailAddressResult.status,
            passwordError = !passwordResult.status,
            confirmPasswordError = !confirmPasswordResult.status,
            clickCheckBoxError = !rulesCheckBoxResult.status
        )


        allValidationsPassed.value = firstNameResult.status && lastNameResult.status &&
                emailAddressResult.status && passwordResult.status && confirmPasswordResult.status
                && rulesCheckBoxResult.status

        if(allValidationsPassed.value){
            callBack(true)
        }
        else{
            callBack(false)
        }
    }

/*****************************************************************************
                        * Use in Register Screen *

Register an account based on the email and password entered by the user
*****************************************************************************/
    private fun register(navController: NavController){
        createUserInFireBase(
            emailAddress = registerUIState.value.emailAddress,
            password = registerUIState.value.confirmPassword,
            navController = navController
        )
    }
    private fun createUserInFireBase(emailAddress : String, password : String, navController: NavController){
        registerUIState.value = registerUIState.value.copy(
            emailAddressIsUsed = false,
            otherRegisterIssues = false
        )

        signUpInProcess.value = true

        FirebaseAuth
           .getInstance()
           .createUserWithEmailAndPassword(emailAddress, password)
           .addOnCompleteListener {
               signUpInProcess.value = false
                if (it.isSuccessful){
                    addAccountInformation { success ->
                        if (success) {
                            navController.navigate("pay")
                        } else {
                            Log.d(TAG, "addAccountInformation failed")
                        }
                    }
                }
           }
           .addOnFailureListener{
               signUpInProcess.value = false
               if (it is FirebaseAuthUserCollisionException) {
                   registerUIState.value = registerUIState.value.copy(
                       emailAddressIsUsed = true
                   )
               } else {
                   registerUIState.value = registerUIState.value.copy(
                       otherRegisterIssues = true
                   )
               }
           }
    }

/*****************************************************************************
                        * Use in Register Screen *

    Initialize database information for newly created accounts
*****************************************************************************/
    private fun addAccountInformation(callback: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = Firebase.firestore

        val emailAddress = registerUIState.value.emailAddress
        val firstName = registerUIState.value.firstName
        val lastName = registerUIState.value.lastName
        val balance = 0.00
        generateUniqueAccount { accountNumber ->
            addAccountToRegisteredAccounts(accountNumber)

            val personalInformation = PersonalInformation(emailAddress, firstName, lastName)
            val transportationCardInformation = TransportationCardInformation(accountNumber, balance)

            val userInformation = hashMapOf(
                "PersonalInformation" to personalInformation,
                "TransportationCardInformation" to transportationCardInformation
            )

            userId?.let {
                db.collection("users").document(userId)
                    .set(userInformation)
                    .addOnSuccessListener {
                        Log.d(TAG, "Account add to DateBase succeed")
                        callback(true)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Account add to DateBase failed", e)
                        callback(false)
                    }
            }?: run {
                Log.w(TAG, "User ID is null")
            }
        }
    }

/*****************************************************************************
                        * Use in Register Screen *

        Generate an unused transportation card number
*****************************************************************************/
    private fun generateRandomAccount(): String {
        return (1..4).joinToString(" ") { Random.nextInt(0, 10000).toString().padStart(4, '0') }
    }

    private fun isAccountExists(accountNumber: String, callback: (Boolean) -> Unit) {
        val db = Firebase.firestore

        db.collection("RegisteredAccounts").document(accountNumber).get()
            .addOnSuccessListener { document ->
                callback(document.exists())
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error checking account existence", e)
                callback(false)
            }
    }

    private fun addAccountToRegisteredAccounts(accountNumber: String) {
        val db = Firebase.firestore
        val accountData = hashMapOf("accountNumber" to accountNumber)

        db.collection("RegisteredAccounts").document(accountNumber)
            .set(accountData)
            .addOnSuccessListener { Log.d(TAG, "Account successfully added to RegisteredAccounts") }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding account to RegisteredAccounts", e) }
    }

    private fun generateUniqueAccount(callback: (String) -> Unit) {
        val accountNumber = generateRandomAccount()
        isAccountExists(accountNumber) { exists ->
            if (exists) {
                generateUniqueAccount(callback)
            } else {
                callback(accountNumber)
            }
        }
    }

/*****************************************************************************
                    * Use in Register Screen *

Detect whether the e-mail address is already registered
*****************************************************************************/
    private fun checkEmailInFireBase(email: String, callBack: (Boolean) -> Unit) {
        registerUIState.value = registerUIState.value.copy(
            emailAddressIsUsed = false
        )
        FirebaseAuth
            .getInstance()
            .fetchSignInMethodsForEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val signInMethods = it.result?.signInMethods
                    Log.d(TAG, "$signInMethods")
                    if (signInMethods?.isEmpty() == true) {
                        registerUIState.value = registerUIState.value.copy(
                            emailAddressIsUsed = false
                        )
                        callBack(true)
                        Log.d(TAG, "Check Email Pass")
                    } else {
                        registerUIState.value = registerUIState.value.copy(
                            emailAddressIsUsed = true
                        )
                        callBack(false)
                        Log.w(TAG, "Check Email Fail")
                    }
                } else {
                    Log.w(TAG, "Check Email Error")
                    callBack(false)
                }
            }
    }
}