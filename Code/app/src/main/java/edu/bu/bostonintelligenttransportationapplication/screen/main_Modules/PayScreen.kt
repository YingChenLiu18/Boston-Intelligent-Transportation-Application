package edu.bu.bostonintelligenttransportationapplication.screen.main_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.ButtonComponent
import edu.bu.bostonintelligenttransportationapplication.components.HeadingTextComponent
import edu.bu.bostonintelligenttransportationapplication.components.QRFunction
import edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payingTheFare.PayingTheFareUIEvent
import edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payingTheFare.PayingTheFireViewModel
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround
import edu.bu.bostonintelligenttransportationapplication.ui.theme.GreyWhite

@Composable
fun PayScreen(navController: NavController) {
    val payingViewModel: PayingTheFireViewModel = viewModel()

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
                    value = "Pay",
                    textColor = GreyWhite
                )
                Spacer(modifier = Modifier.heightIn(40.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Please place the QR code against the scanner to ride the bus",
                        color = GreyWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                    )
                    Spacer(modifier = Modifier.heightIn(10.dp))
                    QRFunction("Successful deduction of fees")
                    Spacer(modifier = Modifier.heightIn(40.dp))
                    Text(
                        text = "OR",
                        color = GreyWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                    )
                    Spacer(modifier = Modifier.heightIn(40.dp))
                    ButtonComponent(
                        value = stringResource(id = R.string.tapToPay),
                        onButtonClick = {
                            payingViewModel.onEvent(PayingTheFareUIEvent.TapButtonClick, navController)
                        },
                        startRange = 120.dp,
                        endRange = 120.dp
                    )
                }
            }
        }

        if (payingViewModel.payingTheFireInProcess.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun DefaultPayScreen(){
    val navController = rememberNavController()
    PayScreen(navController = navController)
}