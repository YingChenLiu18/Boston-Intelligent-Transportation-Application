package edu.bu.bostonintelligenttransportationapplication.data.account_Data.myProfile

data class MyProfileUIState(
    var firstName : String = "",
    var lastName : String = "",
    var originalPassword : String = "",
    var newPassword : String = "",
    var newConfirmPassword : String = "",

    var firstNameError : Boolean = false,
    var lastNameError : Boolean = false,
    var originalPasswordError : Boolean = false,
    var originalPasswordIncorrect : Boolean = false,
    var newPasswordError : Boolean = false,
    var newConfirmPasswordError : Boolean = false,
)