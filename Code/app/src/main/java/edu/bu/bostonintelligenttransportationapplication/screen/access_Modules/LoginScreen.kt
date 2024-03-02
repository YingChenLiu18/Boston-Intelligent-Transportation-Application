package edu.bu.bostonintelligenttransportationapplication.screen.access_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.data.access_Data.login.LogInViewModel
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.*
import edu.bu.bostonintelligenttransportationapplication.data.access_Data.login.LogInUIEvent
import edu.bu.bostonintelligenttransportationapplication.ui.theme.*

@Composable
fun LoginScreen(navController: NavController){
        val logInViewModel: LogInViewModel = viewModel()
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
                    TopReturnBar(title = "Log In", onButtonClick = { navController.navigate("home") })
                    Spacer(modifier = Modifier.heightIn(120.dp))
                    HeadingTextComponent(value = "Welcome Back", textColor = GreyWhite)

                    Spacer(modifier = Modifier.heightIn(40.dp))
                    InformationFieldComponent(
                        labelValue = stringResource(id = R.string.email_address),
                        painterResource = painterResource(id = R.drawable.email),
                        onTextSelected = {
                            logInViewModel.onEvent(LogInUIEvent.EmailAddressChanged(it), navController)
                        },
                        errorState = logInViewModel.loginUIState.value.emailAddressError
                    )
                    PasswordTextFieldComponent(
                        labelValue = stringResource(id = R.string.password),
                        painterResource = painterResource(id = R.drawable.password),
                        onTextSelected = {
                            logInViewModel.onEvent(LogInUIEvent.PasswordChanged(it), navController)
                        },
                        errorState = logInViewModel.loginUIState.value.passwordError
                    )
                    Spacer(modifier = Modifier.heightIn(20.dp))
                    ForgotPasswordComponent(
                        value = stringResource(id = R.string.forgot_password),
                        fontSize = 16.sp,
                        navController
                    )
                    Spacer(modifier = Modifier.heightIn(20.dp))
                    LoginInformationVerification(logInViewModel)
                    ButtonComponent(
                        value = stringResource(id = R.string.login),
                        onButtonClick = {
                            logInViewModel.onEvent(LogInUIEvent.LogInButtonClick, navController)
                        },
                    )
                }
            }

            if (logInViewModel.loginInProcess.value) {
                CircularProgressIndicator()
            }
        }
}

@Composable
fun LoginInformationVerification(logInViewModel: LogInViewModel = viewModel()){
    if (logInViewModel.loginUIState.value.emailAddressError){
        ErrorMessageComponent("Please enter the correct e-mail address")
        Spacer(modifier = Modifier.heightIn(123.dp))
    }
    else if (logInViewModel.loginUIState.value.passwordError){
        ErrorMessageComponent("The password you have entered is not in the correct format")
        Spacer(modifier = Modifier.heightIn(90.dp))
    }
    else if (logInViewModel.loginUIState.value.accountOrPasswordIncorrect){
        ErrorMessageComponent("Invalid Email or Password. Please try Again")
        Spacer(modifier = Modifier.heightIn(123.dp))
    }
    else{
        Spacer(modifier = Modifier.heightIn(150.dp))
    }
}

/*****************************************************************************
                            * Use in Log In Screen *

    Click on the Forgot Password text to go to Retrieve Password Screen
 *****************************************************************************/
@Composable
private fun ForgotPasswordComponent(value: String, fontSize : TextUnit, navController: NavController) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = GrayColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                textDecoration = TextDecoration.Underline)
        ) {
            append(value)
        }
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        ClickableText(
            annotatedString,
            onClick = {navController.navigate("forgotPassword")},
        )
    }
}

@Preview
@Composable
fun DefaultLoginScreen(){
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}