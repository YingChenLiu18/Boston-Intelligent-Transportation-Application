package edu.bu.bostonintelligenttransportationapplication.screen.main_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.UserInformation
import edu.bu.bostonintelligenttransportationapplication.components.MenuRow
import edu.bu.bostonintelligenttransportationapplication.components.PartialLine
import edu.bu.bostonintelligenttransportationapplication.data.access_Data.login.LogInViewModel
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround

data class MenuItem(val title: String, val icon: Int)
@Composable
fun AccountScreen(navController: NavController) {
    val logInViewModel: LogInViewModel = viewModel()

    LaunchedEffect(key1 = true) {
        logInViewModel.getPersonalInformation{}
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
                    .padding(bottom = 30.dp)
            ) {
                UserInformation("${logInViewModel.loginUIState.value.firstName} ${logInViewModel.loginUIState.value.lastName}",
                    logInViewModel.loginUIState.value.emailAddress
                )
                Spacer(modifier = Modifier.heightIn(20.dp))
                MenuRow( MenuItem("My Profile", R.drawable.profile), onItemClick = {navController.navigate("myProfile")})
                PartialLine()
                MenuRow( MenuItem("Purchase History", R.drawable.order), onItemClick = {navController.navigate("purchaseHistory")})
                PartialLine()
                MenuRow( MenuItem("Riding History", R.drawable.order), onItemClick = {navController.navigate("ridingHistory")})
                Spacer(modifier = Modifier.weight(1f))
                MenuRow( MenuItem("Sign Out", R.drawable.signout), onItemClick = {logout(navController)})
            }
        }

        if (logInViewModel.loginInProcess.value) {
            CircularProgressIndicator()
        }
    }
}

fun logout(navController: NavController){
    val firebaseAuth = FirebaseAuth.getInstance()
    firebaseAuth.signOut()
    val authStateListener = FirebaseAuth.AuthStateListener {
        if (it.currentUser == null) {
            navController.navigate("home"){
                popUpTo("home") {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    firebaseAuth.addAuthStateListener(authStateListener)
}
@Preview
@Composable
fun DefaultAccountScreen(){
    val navController = rememberNavController()
    AccountScreen(navController = navController)
}