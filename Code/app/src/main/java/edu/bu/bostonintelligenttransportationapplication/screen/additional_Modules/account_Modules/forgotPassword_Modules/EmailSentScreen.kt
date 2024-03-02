package edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.forgotPassword_Modules

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround
import edu.bu.bostonintelligenttransportationapplication.ui.theme.GreyWhite
import edu.bu.bostonintelligenttransportationapplication.ui.theme.LightGreen

@Composable
fun EmailSentScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGround)
        ) {
            Column(modifier = Modifier.fillMaxSize().background(BackGround),  horizontalAlignment = Alignment.CenterHorizontally) {
                TopReturnBar(title = "Forgot Password", onButtonClick = { navController.navigate("login") })
                Spacer(modifier = Modifier.heightIn(120.dp))
                Icon(
                    painter = painterResource(id = R.drawable.forgot_password_email),
                    contentDescription = "Email Icon",
                    tint = LightGreen,
                    modifier = Modifier.size(160.dp)
                )
                Spacer(modifier = Modifier.heightIn(16.dp))
                Text(
                    text = "Check Your Email",
                    style = TextStyle(color = GreyWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.heightIn(16.dp))
                Text(
                    color = GreyWhite,
                    text = "We've sent a reset password link to your email. Please check and follow the instructions.",
                    style = TextStyle(fontSize = 16.sp),
                    textAlign = TextAlign.Center
                )
                Text(
                    color = GreyWhite,
                    text = "If not received, Please check your spam folder.",
                    style = TextStyle(fontSize = 16.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultBackToStartScreenButton(){
    val navController = rememberNavController()
    EmailSentScreen(navController = navController)
}