package edu.bu.bostonintelligenttransportationapplication.data.access_Data.register

data class RegisterUIState(
    var firstName : String = "",
    var lastName : String = "",
    var emailAddress : String = "",
    var password : String = "",
    var confirmPassword : String = "",
    var clickCheckBoxAccepted : Boolean = false,

    var firstNameError : Boolean = false,
    var lastNameError : Boolean = false,
    var emailAddressError : Boolean = false,
    var passwordError : Boolean = false,
    var confirmPasswordError : Boolean = false,
    var clickCheckBoxError : Boolean = false,

    var emailAddressIsUsed : Boolean = false,
    var otherRegisterIssues : Boolean = false
)