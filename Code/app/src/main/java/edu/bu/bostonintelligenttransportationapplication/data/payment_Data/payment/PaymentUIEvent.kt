package edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payment



sealed class PaymentUIEvent {
    data class CreditCardNumberChanged(val cardNumber : String) : PaymentUIEvent()
    data class CardholderNameChanged(val cardholderName : String) : PaymentUIEvent()
    data class ExpirationMonthChanged(val expirationMonth : String) : PaymentUIEvent()
    data class ExpirationYearChanged(val expirationYear : String) : PaymentUIEvent()
    data class CvvChanged(val cvv : String) : PaymentUIEvent()
    data class PaymentAmountChanged(val paymentAmount : String) : PaymentUIEvent()
    object ConfirmButtonClick : PaymentUIEvent()
}