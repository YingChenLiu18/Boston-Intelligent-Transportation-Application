package edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payingTheFare



sealed class PayingTheFareUIEvent {
    object TapButtonClick : PayingTheFareUIEvent()
}