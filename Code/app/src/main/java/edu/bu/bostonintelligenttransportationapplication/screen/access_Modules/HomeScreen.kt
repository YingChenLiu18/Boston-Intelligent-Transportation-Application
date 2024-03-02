package edu.bu.bostonintelligenttransportationapplication.screen.access_Modules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.ui.theme.*

@Composable
fun HomeScreen(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val backgroundImage: Painter = painterResource(id = R.drawable.starter_screen)

        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(text = "Boston Intelligent Transportation",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp),
            style = TextStyle(
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif
            ),
            color = GreyWhite,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(start = 16.dp, end = 16.dp, bottom = 80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.weight(1f).height(50.dp).padding(end = 8.dp),
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonGreen),
                shape = RoundedCornerShape(10.dp)
            ) {
                    Text(text = "Sign In", fontSize = 20.sp, color = Color.White)
            }


            Spacer(modifier = Modifier.widthIn(10.dp))

            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier.weight(1f).height(50.dp).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonGreen),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Register", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun DefaultStartScreen(){
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}