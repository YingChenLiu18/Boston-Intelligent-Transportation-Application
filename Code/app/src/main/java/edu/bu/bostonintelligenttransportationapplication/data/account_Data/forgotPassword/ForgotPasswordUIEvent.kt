package edu.bu.bostonintelligenttransportationapplication.data.account_Data.forgotPassword

sealed class ForgotPasswordUIEvent{

    data class EmailAddressEntered(val emailAddress : String) : ForgotPasswordUIEvent()
    object ResetButtonClick : ForgotPasswordUIEvent()
}
