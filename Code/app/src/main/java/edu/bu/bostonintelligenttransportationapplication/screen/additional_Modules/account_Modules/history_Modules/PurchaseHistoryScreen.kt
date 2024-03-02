package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.history_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.components.PurchaseHistoryList
import edu.bu.bostonintelligenttransportationapplication.data.account_Data.purchaseHistory.PurchaseHistoryViewModel
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround

@Composable
fun PurchaseHistoryScreen(navController: NavController){
    val purchaseHistoryViewModel: PurchaseHistoryViewModel = viewModel()
    LaunchedEffect(key1 = true) {
        purchaseHistoryViewModel.getPurchaseHistory()
    }

    val purchaseHistory = purchaseHistoryViewModel.purchaseHistoryUIState.value.purchaseHistory.reversed()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGround)
            ) {
                TopReturnBar(title = "Purchase History", onButtonClick = { navController.navigate("account") })
                PurchaseHistoryList(purchaseHistory = purchaseHistory)
            }
        }

        if (purchaseHistoryViewModel.purchaseHistoryInProcess.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun DefaultPurchaseHistoryScreen(){
    val navController = rememberNavController()
    PurchaseHistoryScreen(navController = navController)
}