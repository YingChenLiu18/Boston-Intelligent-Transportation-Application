package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.payment_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import edu.bu.bostonintelligenttransportationapplication.ui.theme.ButtonGreen
import edu.bu.bostonintelligenttransportationapplication.ui.theme.GreyWhite

@Composable
fun FailureToPayScreen(navController: NavController){
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
                TopReturnBar(title = "Failure To Pay", onButtonClick = { navController.navigate("pay") })
                Spacer(modifier = Modifier.heightIn(200.dp))
                Text(
                    text = "Your account balance is insufficient",
                    color = GreyWhite,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )
                Spacer(modifier = Modifier.heightIn(10.dp))
                Text(
                    text = "Please recharge your account",
                    color = GreyWhite,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )
                Spacer(modifier = Modifier.heightIn(20.dp))
                Icon(
                    painter = painterResource(id = R.drawable.crying_face),
                    contentDescription = "Payment Successful",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.heightIn(100.dp))
                Button(
                    onClick = { navController.navigate("purchase") },
                    modifier = Modifier.widthIn(80.dp).height(40.dp).padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonGreen),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Recharge", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultFailureToPayScreen(){
    val navController = rememberNavController()
    FailureToPayScreen(navController = navController)
}