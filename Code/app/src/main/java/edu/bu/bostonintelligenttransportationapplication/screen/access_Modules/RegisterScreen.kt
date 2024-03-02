package edu.bu.bostonintelligenttransportationapplication.screen.access_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.components.*
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.data.access_Data.register.RegisterViewModel
import edu.bu.bostonintelligenttransportationapplication.data.access_Data.register.RegisterUIEvent
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround
import edu.bu.bostonintelligenttransportationapplication.ui.theme.CustomGrey
import edu.bu.bostonintelligenttransportationapplication.ui.theme.GrayColor
import edu.bu.bostonintelligenttransportationapplication.ui.theme.GreyWhite


@Composable
fun RegisterScreen(navController: NavController){
    val registerViewModel: RegisterViewModel = viewModel()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround)
        ){
            Column(modifier = Modifier
                .fillMaxSize()
                .background(BackGround)
            ) {
                TopReturnBar(title = "Register", onButtonClick = { navController.navigate("home") })
                Spacer(modifier = Modifier.heightIn(20.dp))
                HeadingTextComponent(value = "Create an Account", textColor = GreyWhite)
                Spacer(modifier = Modifier.heightIn(25.dp))
                InformationFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        registerViewModel.onEvent(RegisterUIEvent.FirstNameChanged(it), navController)
                    },
                    errorState = registerViewModel.registerUIState.value.firstNameError,
                )

                InformationFieldComponent(
                    labelValue = stringResource(id = R.string.last_name),
                    painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        registerViewModel.onEvent(RegisterUIEvent.LastNameChanged(it), navController)
                    },
                    errorState = registerViewModel.registerUIState.value.lastNameError,
                )

                InformationFieldComponent(
                    labelValue = stringResource(id = R.string.email_address),
                    painterResource = painterResource(id = R.drawable.email),
                    onTextSelected = {
                        registerViewModel.onEvent(RegisterUIEvent.EmailAddressChanged(it), navController)
                    },
                    errorState = registerViewModel.registerUIState.value.emailAddressError,
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.password),
                    onTextSelected = {
                        registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it), navController)
                    },
                    errorState = registerViewModel.registerUIState.value.passwordError,
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.confirm_password),
                    painterResource = painterResource(id = R.drawable.password),
                    onTextSelected = {
                        registerViewModel.onEvent(RegisterUIEvent.ConfirmPasswordChanged(it), navController)
                    },
                    errorState = registerViewModel.registerUIState.value.confirmPasswordError,
                )

                RulesCheckBoxComponent(
                    onCheckedChange = {registerViewModel.onEvent(RegisterUIEvent.RulesCheckBoxClick(it), navController)},
                    navController = navController
                )

                RegistrationInformationVerification()
                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClick = {
                        registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClick, navController)
                    }
                )
            }
        }

        if(registerViewModel.signUpInProcess.value){
            CircularProgressIndicator()
        }
    }
}

@Composable
fun RegistrationInformationVerification(registerViewModel: RegisterViewModel = viewModel()){
    if (registerViewModel.registerUIState.value.emailAddressIsUsed){
        ErrorMessageComponent("Email Address is used")
        Spacer(modifier = Modifier.heightIn(33.dp))
    }
    else if (registerViewModel.registerUIState.value.firstNameError || registerViewModel.registerUIState.value.lastNameError){
        ErrorMessageComponent("The First and Last names must contain at least two or more letters!")
        Spacer(modifier = Modifier.heightIn(17.dp))
    }
    else if (registerViewModel.registerUIState.value.emailAddressError){
        ErrorMessageComponent("Incorrect email format")
        Spacer(modifier = Modifier.heightIn(33.dp))
    }
    else if (registerViewModel.registerUIState.value.passwordError){
        ErrorMessageComponent("The password must be at least 6 digits long and must contain at least one lowercase letter, uppercase letter, number and special character")
    }
    else if (registerViewModel.registerUIState.value.confirmPasswordError){
        ErrorMessageComponent("Confirm password is different from Password")
        Spacer(modifier = Modifier.heightIn(33.dp))
    }
    else if (registerViewModel.registerUIState.value.clickCheckBoxError){
            ErrorMessageComponent("You must agree to the terms of the application")
        Spacer(modifier = Modifier.heightIn(33.dp))
    }
    else if (registerViewModel.registerUIState.value.otherRegisterIssues) {
        ErrorMessageComponent("An error was encountered here, please try again or contact customer service")
        Spacer(modifier = Modifier.heightIn(33.dp))
    }
    else{
        Spacer(modifier = Modifier.heightIn(60.dp))
    }
}

/*****************************************************************************
                        * Use in Register Screen *

    Click box ensures that the user has agreed to the terms of the application
 *****************************************************************************/

@Composable
private fun RulesCheckBoxComponent(
    onCheckedChange : (Boolean) -> Unit,
    startRange : Dp = 25.dp,
    endRange : Dp = 25.dp,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .padding(start = startRange, end = endRange),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val checkedState = remember {
            mutableStateOf(false)
        }

        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                onCheckedChange.invoke(it)
            },
            colors = CheckboxDefaults.colors(
                checkmarkColor = GreyWhite,
                uncheckedColor = GreyWhite,
                checkedColor = GrayColor
            )
        )

        ClickAbleRulesText(navController)
    }
}

/*****************************************************************************
                        * Use in Register Screen *

    Click on the Privacy Policy or Term of Use text to go to term Screen
 *****************************************************************************/
@Composable
private fun ClickAbleRulesText(navController: NavController) {
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy"
    val andText = " and "
    val termsAndConditionsText = "Term of Use"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = GreyWhite)) {
            append(initialText)
        }
        withStyle(style = SpanStyle(color = CustomGrey)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        withStyle(style = SpanStyle(color = GreyWhite)) {
            append(andText)
        }
        withStyle(style = SpanStyle(color = CustomGrey)) {
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->

        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                if (span.item == termsAndConditionsText) {
                    navController.navigate("termsAndConditions")
                }else if (span.item == privacyPolicyText){
                    navController.navigate("privacyPolicy")
                }
            }
    })
}

@Preview
@Composable
fun DefaultSignUpScreen(){
    val navController = rememberNavController()
    RegisterScreen(navController = navController)
}