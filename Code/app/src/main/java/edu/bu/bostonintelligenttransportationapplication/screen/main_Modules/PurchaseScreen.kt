package edu.bu.bostonintelligenttransportationapplication.screen.main_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.components.HeadingTextComponent
import edu.bu.bostonintelligenttransportationapplication.components.PurchaseSystem
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround
import edu.bu.bostonintelligenttransportationapplication.ui.theme.GreyWhite

@Composable
fun PurchaseScreen(navController: NavController) {
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
                    value = "Recharge Your Card",
                    textColor = GreyWhite
                )

                Spacer(modifier = Modifier.heightIn(60.dp))

                PurchaseSystem(navController)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPurchaseScreen(){
    val navController = rememberNavController()
    PurchaseScreen(navController = navController)
}