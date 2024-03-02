package edu.bu.bostonintelligenttransportationapplication.screen.main_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.bu.bostonintelligenttransportationapplication.components.AccountInformationComponents
import edu.bu.bostonintelligenttransportationapplication.components.HeadingTextComponent
import edu.bu.bostonintelligenttransportationapplication.components.TransportationCard
import edu.bu.bostonintelligenttransportationapplication.data.account_Data.transportationCard.TransportationCardViewModel
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround
import edu.bu.bostonintelligenttransportationapplication.ui.theme.GreyWhite

@Composable
fun CardScreen() {
    val transportationCardViewModel: TransportationCardViewModel = viewModel()

    LaunchedEffect(key1 = true) {
        transportationCardViewModel.getTransportationCardInformation()
    }

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
                Spacer(modifier = Modifier.heightIn(20.dp))
                HeadingTextComponent(
                    value = "Card",
                    textColor = GreyWhite
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                TransportationCard(cardNumber = transportationCardViewModel.transportationCardUIState.value.transportationCardID)
                AccountInformationComponents(
                    balance = String.format("%.2f", transportationCardViewModel.transportationCardUIState.value.transportationCardBalance),
                    passDueDate = transportationCardViewModel.transportationCardUIState.value.transportationCardPass
                )
            }
        }

        if (transportationCardViewModel.transportationCardInProcess.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun DefaultCardScreen(){
    CardScreen()
}