package edu.bu.bostonintelligenttransportationapplication.data.account_Data.purchaseHistory

import edu.bu.bostonintelligenttransportationapplication.data.rules.RechargeRecord

data class PurchaseHistoryUIState(
    val purchaseHistory: List<RechargeRecord> = listOf()
)