package edu.bu.bostonintelligenttransportationapplication.data.account_Data.rideHistory

import edu.bu.bostonintelligenttransportationapplication.data.rules.SpendingRecord

data class RideHistoryUIState(
    val rideHistory: List<SpendingRecord> = listOf()
)