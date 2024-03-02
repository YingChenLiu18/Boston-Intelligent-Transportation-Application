package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.forgotPassword_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.ButtonComponent
import edu.bu.bostonintelligenttransportationapplication.components.ErrorMessageComponent
import edu.bu.bostonintelligenttransportationapplication.components.InformationFieldComponent
import edu.bu.bostonintelligenttransportationapplication.components.CommonTextComponents
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.data.account_Data.forgotPassword.ForgotPasswordUIEvent
import edu.bu.bostonintelligenttransportationapplication.data.account_Data.forgotPassword.ForgotPasswordViewModel
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround)
        ) {
            Column(modifier = Modifier.fillMaxSize().background(BackGround)) {
                TopReturnBar(title = "Forgot Password", onButtonClick = { navController.navigate("login") })
                Spacer(modifier = Modifier.heightIn(20.dp))
                CommonTextComponents(
                    stringResource(id = R.string.forgot_password_text),
                    14.sp,
                    FontWeight.Normal
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                InformationFieldComponent(
                    labelValue = stringResource(id = R.string.email_address),
                    painterResource = painterResource(id = R.drawable.email),
                    onTextSelected = {
                        forgotPasswordViewModel.onEvent(
                            ForgotPasswordUIEvent.EmailAddressEntered(it),
                            navController
                        )
                    },
                    errorState = forgotPasswordViewModel.forgotPasswordUIState.value.emailAddressError
                )
                LoginInformationVerification(forgotPasswordViewModel)
                ButtonComponent(
                    value = "Send Reset Link",
                    onButtonClick = {forgotPasswordViewModel.onEvent(ForgotPasswordUIEvent.ResetButtonClick, navController) },
                    startRange = 25.dp,
                    endRange = 25.dp
                )
                Spacer(modifier = Modifier.heightIn(360.dp))
            }
        }

        if (forgotPasswordViewModel.forgotPasswordInProcess.value) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun LoginInformationVerification(forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()) {
    if (forgotPasswordViewModel.forgotPasswordUIState.value.emailAddressError) {
        ErrorMessageComponent("Please enter the correct e-mail address")
        Spacer(modifier = Modifier.heightIn(3.dp))
    }
    else if(forgotPasswordViewModel.forgotPasswordUIState.value.emailAddressIsNotUsed){
        ErrorMessageComponent("This E-mail address is not registered")
        Spacer(modifier = Modifier.heightIn(3.dp))
    }
    else{
        Spacer(modifier = Modifier.heightIn(30.dp))
    }

}

@Preview
@Composable
fun DefaultForgotPasswordScreen(){
    val navController = rememberNavController()
    ForgotPasswordScreen(navController = navController)
}
