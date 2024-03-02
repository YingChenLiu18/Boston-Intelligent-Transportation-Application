package edu.bu.bostonintelligenttransportationapplication.data.access_Data.login

data class LogInUIState(
    var emailAddress : String = "",
    var password : String = "",

    var lastName : String = "",
    var firstName : String = "",

    var emailAddressError : Boolean = false,
    var passwordError : Boolean = false,

    var accountOrPasswordIncorrect : Boolean = false,
)