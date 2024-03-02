package edu.bu.bostonintelligenttransportationapplication.data.access_Data.login

sealed class LogInUIEvent{

    data class EmailAddressChanged(val emailAddress : String) : LogInUIEvent()
    data class PasswordChanged(val password : String) : LogInUIEvent()
    object LogInButtonClick : LogInUIEvent()
}
