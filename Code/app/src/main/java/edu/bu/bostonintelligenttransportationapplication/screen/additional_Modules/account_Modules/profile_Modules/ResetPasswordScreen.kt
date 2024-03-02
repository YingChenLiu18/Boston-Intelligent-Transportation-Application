package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.profile_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.ButtonComponent
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.components.ErrorMessageComponent
import edu.bu.bostonintelligenttransportationapplication.components.PasswordTextFieldComponent
import edu.bu.bostonintelligenttransportationapplication.data.account_Data.myProfile.MyProfileUIEvent
import edu.bu.bostonintelligenttransportationapplication.data.account_Data.myProfile.MyProfileViewModel
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround
import edu.bu.bostonintelligenttransportationapplication.ui.theme.MenuRowFont

@Composable
fun ResetPasswordScreen(navController: NavController){
    val myProfileViewModel: MyProfileViewModel = viewModel()
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
                TopReturnBar(title = "Reset Password", onButtonClick = { navController.navigate("myProfile") })
                Spacer(modifier = Modifier.heightIn(40.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.original_password),
                    painterResource = painterResource(id = R.drawable.password),
                    onTextSelected = {
                        myProfileViewModel.onEvent(MyProfileUIEvent.OriginalPasswordChanged(it), navController)
                    },
                    errorState = myProfileViewModel.myProfileUIState.value.originalPasswordError,
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.new_password),
                    painterResource = painterResource(id = R.drawable.password),
                    onTextSelected = {
                        myProfileViewModel.onEvent(MyProfileUIEvent.NewPasswordChanged(it), navController)
                    },
                    errorState = myProfileViewModel.myProfileUIState.value.newPasswordError,
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                PasswordRequirementsBox()
                Spacer(modifier = Modifier.heightIn(10.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.new_confirm_password),
                    painterResource = painterResource(id = R.drawable.password),
                    onTextSelected = {
                        myProfileViewModel.onEvent(MyProfileUIEvent.NewConfirmPasswordChanged(it), navController)
                    },
                    errorState = myProfileViewModel.myProfileUIState.value.newConfirmPasswordError,
                )
                RestPasswordVerification()
                ButtonComponent(
                    value = stringResource(id = R.string.save_password),
                    onButtonClick = {
                        myProfileViewModel.onEvent(MyProfileUIEvent.RestPasswordButtonClick, navController)
                    }
                )
            }
        }

        if(myProfileViewModel.myProfileInProcess.value){
            CircularProgressIndicator()
        }
    }
}

@Composable
fun RestPasswordVerification(myProfileViewModel: MyProfileViewModel = viewModel()){
    if (myProfileViewModel.myProfileUIState.value.originalPasswordError){
        ErrorMessageComponent("The format of the Original Password is not correct")
        Spacer(modifier = Modifier.heightIn(53.dp))
    }
    else if (myProfileViewModel.myProfileUIState.value.newPasswordError){
        ErrorMessageComponent("The format of the New Password is not correct")
        Spacer(modifier = Modifier.heightIn(53.dp))
    }
    else if (myProfileViewModel.myProfileUIState.value.newConfirmPasswordError){
        ErrorMessageComponent("Confirm New Password is different from New Password")
        Spacer(modifier = Modifier.heightIn(36.dp))
    }
    else if (myProfileViewModel.myProfileUIState.value.originalPasswordIncorrect){
        ErrorMessageComponent("The Original Password is not correct")
        Spacer(modifier = Modifier.heightIn(53.dp))
    }
    else{
        Spacer(modifier = Modifier.heightIn(80.dp))
    }
}

@Composable
private fun PasswordRequirementItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp, top = 2.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.check_circle),
            contentDescription = "Check icon",
            tint = MenuRowFont,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            color = MenuRowFont,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun PasswordRequirementsBox() {
    Column(modifier = Modifier.padding(16.dp)) {
        PasswordRequirementItem("Must contain at least one letter")
        PasswordRequirementItem("Must contain at least one capital letter")
        PasswordRequirementItem("Must contain at least one number")
        PasswordRequirementItem("Must contain at least 8 characters")
    }
}

@Preview
@Composable
fun PreviewResetPasswordScreen(){
    val navController = rememberNavController()
    ResetPasswordScreen(navController = navController)
}