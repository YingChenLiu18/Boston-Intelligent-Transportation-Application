package edu.bu.bostonintelligenttransportationapplication.data.account_Data.myProfile

sealed class MyProfileUIEvent{

    data class FirstNameChanged(val firstName : String) : MyProfileUIEvent()
    data class LastNameChanged(val lastName : String) : MyProfileUIEvent()
    data class OriginalPasswordChanged(val originalPassword : String) : MyProfileUIEvent()
    data class NewPasswordChanged(val newPassword : String) : MyProfileUIEvent()
    data class NewConfirmPasswordChanged(val newConfirmPassword : String) : MyProfileUIEvent()
    object RestPasswordButtonClick : MyProfileUIEvent()
    object EditNameButtonClick : MyProfileUIEvent()
}
