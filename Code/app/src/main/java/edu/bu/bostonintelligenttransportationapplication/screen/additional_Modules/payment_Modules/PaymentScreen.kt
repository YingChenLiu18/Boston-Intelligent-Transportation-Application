package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.payment_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.ButtonComponent
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.components.Cvv
import edu.bu.bostonintelligenttransportationapplication.components.ErrorMessageComponent
import edu.bu.bostonintelligenttransportationapplication.components.ExpirationDatePicker
import edu.bu.bostonintelligenttransportationapplication.components.HeadingTextComponent
import edu.bu.bostonintelligenttransportationapplication.components.InformationFieldComponent
import edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payment.PaymentUIEvent
import edu.bu.bostonintelligenttransportationapplication.data.payment_Data.payment.PaymentViewModel
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround
import edu.bu.bostonintelligenttransportationapplication.ui.theme.MenuRowFont


@Composable
fun PaymentScreen(navController: NavController, price : String) {
    val paymentViewModel: PaymentViewModel = viewModel()
    paymentViewModel.onEvent(PaymentUIEvent.PaymentAmountChanged(price), navController)

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
                TopReturnBar(title = "Make A Payment", onButtonClick = { navController.navigate("purchase") })
                Spacer(modifier = Modifier.heightIn(20.dp))
                HeadingTextComponent(value = "Total Amount: $ ${paymentViewModel.paymentUIState.value.paymentAmount}", textColor = MenuRowFont, fontSize = 25.sp)
                Spacer(modifier = Modifier.heightIn(20.dp))
                InformationFieldComponent(
                    labelValue = stringResource(id = R.string.nameOnCard),
                    painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        paymentViewModel.onEvent(PaymentUIEvent.CardholderNameChanged(it), navController)
                    },
                    errorState = paymentViewModel.paymentUIState.value.cardholderNameError,
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                InformationFieldComponent(
                    labelValue = stringResource(id = R.string.creditCard),
                    painterResource = painterResource(id = R.drawable.creditcard),
                    onTextSelected = {
                        paymentViewModel.onEvent(PaymentUIEvent.CreditCardNumberChanged(it), navController)
                    },
                    errorState = paymentViewModel.paymentUIState.value.cardNumberError,
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                Row{
                    ExpirationDatePicker(
                        onExpiryMonthChanged = {
                            paymentViewModel.onEvent(
                                PaymentUIEvent.ExpirationMonthChanged(it), navController
                            )
                        },
                        onExpiryYearChanged = {
                            paymentViewModel.onEvent(
                                PaymentUIEvent.ExpirationYearChanged(it), navController
                            )
                        }
                    )

                    Spacer(modifier = Modifier.widthIn(5.dp))

                    Cvv(
                        labelValue = stringResource(id = R.string.cvv),
                        onTextSelected = {
                            paymentViewModel.onEvent(
                                PaymentUIEvent.CvvChanged(it),
                                navController
                            )
                        },
                        errorState = paymentViewModel.paymentUIState.value.cvvError,
                    )
                }
                PaymentVerification()
                ButtonComponent(
                    value = stringResource(id = R.string.confirm),
                    onButtonClick = {
                        paymentViewModel.onEvent(PaymentUIEvent.ConfirmButtonClick, navController)
                    },
                )
            }
        }
        if (paymentViewModel.paymentInProcess.value) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PaymentVerification(paymentViewModel: PaymentViewModel = viewModel()){
    if (paymentViewModel.paymentUIState.value.cardNumberError){
        ErrorMessageComponent("Card number is not in the correct format, please check and re-enter it")
        Spacer(modifier = Modifier.heightIn(57.dp))
    }
    else if (paymentViewModel.paymentUIState.value.cardholderNameError){
        ErrorMessageComponent("Please enter the correct name of the cardholder")
        Spacer(modifier = Modifier.heightIn(73.dp))
    }
    else if (paymentViewModel.paymentUIState.value.expirationDataError){
        ErrorMessageComponent("Card expiration date is invalid, please enter the correct month and year")
        Spacer(modifier = Modifier.heightIn(57.dp))
    }
    else if (paymentViewModel.paymentUIState.value.cvvError){
        ErrorMessageComponent("Please enter the correct 3 or 4 digit Cvv code")
        Spacer(modifier = Modifier.heightIn(73.dp))
    }
    else if (paymentViewModel.paymentUIState.value.creditCardIsInvalid){
        ErrorMessageComponent("Could not verify your card information, please confirm and try again")
        Spacer(modifier = Modifier.heightIn(57.dp))
    }
    else{
        Spacer(modifier = Modifier.heightIn(100.dp))
    }
}

@Preview
@Composable
fun DefaultPaymentScreen(){
    val navController = rememberNavController()
    PaymentScreen(navController = navController, "0")
}