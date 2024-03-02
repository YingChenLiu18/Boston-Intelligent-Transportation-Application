package edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payment

data class PaymentUIState(
    var cardNumber : String =  "",
    var cardholderName : String  = "",
    var expirationMonth : String  = "",
    var expirationYear : String  = "",
    var cvv : String  = "",
    var paymentAmount : String = "",

    var cardNumberError : Boolean = false,
    var cardholderNameError : Boolean = false,
    var cvvError  : Boolean = false,
    var expirationDataError : Boolean = false,

    var creditCardIsInvalid : Boolean = false,
)