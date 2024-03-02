package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.profile_Modules

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.ButtonComponent
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.components.InformationFieldComponent
import edu.bu.bostonintelligenttransportationapplication.data.account_Data.myProfile.MyProfileUIEvent
import edu.bu.bostonintelligenttransportationapplication.data.account_Data.myProfile.MyProfileViewModel
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround

@Composable
fun EditNameScreen(navController: NavController){
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
                TopReturnBar(title = "Edit Name", onButtonClick = { navController.navigate("myProfile") })
                Spacer(modifier = Modifier.heightIn(40.dp))
                InformationFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        myProfileViewModel.onEvent(MyProfileUIEvent.FirstNameChanged(it), navController)
                    },
                    errorState = myProfileViewModel.myProfileUIState.value.firstNameError,
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                InformationFieldComponent(
                    labelValue = stringResource(id = R.string.last_name),
                    painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        myProfileViewModel.onEvent(MyProfileUIEvent.LastNameChanged(it), navController)
                    },
                    errorState = myProfileViewModel.myProfileUIState.value.lastNameError,
                )
                Spacer(modifier = Modifier.heightIn(70.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.save_name),
                    onButtonClick = {
                        myProfileViewModel.onEvent(MyProfileUIEvent.EditNameButtonClick, navController)
                    },
                )
            }
        }

        if(myProfileViewModel.myProfileInProcess.value){
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun PreviewEditNameScreen(){
    val navController = rememberNavController()
    EditNameScreen(navController = navController)
}