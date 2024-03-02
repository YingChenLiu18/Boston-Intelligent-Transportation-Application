package edu.bu.bostonintelligenttransportationapplication.data.account_Data.forgotPassword

data class ForgotPasswordUIState(
    var emailAddress : String = "",

    var emailAddressError : Boolean = false,
    var emailAddressIsNotUsed : Boolean = false
)