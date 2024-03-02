package edu.bu.bostonintelligenttransportationapplication.data.rules

data class PersonalInformation(
    val emailAddress: String,
    val firstName: String,
    val lastName: String
)

data class TransportationCardInformation(
    val accountNumber: String,
    val accountBalance: Double,
    var passExpirationDate: String? = null,
    val accountRechargeRecords: MutableList<RechargeRecord> = mutableListOf(),
    val accountSpendingRecords: MutableList<SpendingRecord> = mutableListOf()
)

data class RechargeRecord(
    val creditCardAccount: String,
    val rechargeTime: String,
    val rechargeAction: String,
    val currentBalance: String
)

data class SpendingRecord(
    val ridingTime: String,
    val deductionAction: String,
    val currentBalance: String
)