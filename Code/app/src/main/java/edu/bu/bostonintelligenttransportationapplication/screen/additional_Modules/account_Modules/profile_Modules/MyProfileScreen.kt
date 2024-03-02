package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.profile_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.components.MenuRow
import edu.bu.bostonintelligenttransportationapplication.components.PartialLine
import edu.bu.bostonintelligenttransportationapplication.screen.main_Modules.MenuItem
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround

@Composable
fun MyProfileScreen(navController: NavController) {
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
                TopReturnBar(title = "My Profile", onButtonClick = { navController.navigate("account") })
                Spacer(modifier = Modifier.heightIn(10.dp))
                MenuRow( MenuItem("Name", R.drawable.profile), onItemClick = {navController.navigate("editName")})
                PartialLine()
                MenuRow( MenuItem("Reset Password", R.drawable.password), onItemClick = {navController.navigate("resetPassword")})
            }
        }
    }
}

@Preview
@Composable
fun DefaultMyProfileScreen(){
    val navController = rememberNavController()
    MyProfileScreen(navController = navController)
}