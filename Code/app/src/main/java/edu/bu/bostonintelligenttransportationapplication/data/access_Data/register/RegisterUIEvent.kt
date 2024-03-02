package edu.bu.bostonintelligenttransportationapplication.data.access_Data.register

sealed class RegisterUIEvent{
    data class FirstNameChanged(val firstName : String) : RegisterUIEvent()
    data class LastNameChanged(val lastName : String) : RegisterUIEvent()
    data class EmailAddressChanged(val emailAddress : String) : RegisterUIEvent()
    data class PasswordChanged(val password : String) : RegisterUIEvent()
    data class ConfirmPasswordChanged(val confirmPassword : String) : RegisterUIEvent()
    data class  RulesCheckBoxClick(val status : Boolean) : RegisterUIEvent()
    object RegisterButtonClick : RegisterUIEvent()
}
