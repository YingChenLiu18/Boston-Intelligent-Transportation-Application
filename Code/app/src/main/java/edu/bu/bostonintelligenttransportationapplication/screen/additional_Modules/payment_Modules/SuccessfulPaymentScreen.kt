package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.payment_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround
import edu.bu.bostonintelligenttransportationapplication.ui.theme.GreyWhite


@Composable
fun SuccessfulPaymentScreen(navController: NavController){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGround),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopReturnBar(title = "Payment", onButtonClick = { navController.navigate("card") })
                Spacer(modifier = Modifier.heightIn(200.dp))
                Text(
                    text = "Thank You!",
                    color = GreyWhite,
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )

                Text(
                    text = "Your payment was successful",
                    color = GreyWhite,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )
                Spacer(modifier = Modifier.heightIn(20.dp))
                Icon(
                    painter = painterResource(id = R.drawable.thank_you),
                    contentDescription = "Payment Successful",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(128.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultSuccessfulPaymentScreen(){
    val navController = rememberNavController()
    SuccessfulPaymentScreen(navController = navController)
}