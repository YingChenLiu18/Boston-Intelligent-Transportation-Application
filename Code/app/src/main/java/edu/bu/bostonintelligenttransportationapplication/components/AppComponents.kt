package edu.bu.bostonintelligenttransportationapplication.components

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.bu.bostonintelligenttransportationapplication.ui.theme.*
import edu.bu.bostonintelligenttransportationapplication.R
import edu.bu.bostonintelligenttransportationapplication.screen.main_Modules.MenuItem
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

import edu.bu.bostonintelligenttransportationapplication.data.rules.RechargeRecord
import edu.bu.bostonintelligenttransportationapplication.data.rules.SpendingRecord
import java.util.Calendar

/*****************************************************************************
                        * Used in almost Screen *

    Display Screen's topics
*****************************************************************************/
@Composable
fun HeadingTextComponent(value: String, textColor: Color, fontSize : TextUnit = 30.sp) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = textColor,
        textAlign = TextAlign.Center
    )
}

/*****************************************************************************
                        * Used in almost Screen *

    Display Common Text
 *****************************************************************************/
@Composable
fun CommonTextComponents(
    value: String,
    fontSize: TextUnit,
    textWeight: FontWeight,
    startRange : Dp = 25.dp,
    endRange : Dp = 25.dp
) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn()
            .padding(start = startRange, end = endRange),
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = textWeight,
            fontStyle = FontStyle.Normal
        ),
        color = GreyWhite,
        textAlign = TextAlign.Start
    )
}


/*****************************************************************************
    * Used in Login, registration and other data interaction Screen *

    Let the user enter some information that is always visible
 *****************************************************************************/
@Composable
fun InformationFieldComponent(
    labelValue : String,
    painterResource : Painter,
    onTextSelected : (String) -> Unit,
    errorState : Boolean = false,
    startRange : Dp = 25.dp,
    endRange : Dp = 25.dp
    ) {
    val textValue = remember {
        mutableStateOf("")
    }
    Column {
        Text(
            text = "* $labelValue",
            color = MenuRowFont,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = startRange, top = 2.dp)
                .align(Alignment.Start)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .padding(start = startRange, end = endRange),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                cursorColor = Color.White,
                backgroundColor = GreyWhite
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onTextSelected(it)
            },
            leadingIcon = {
                Icon(painter = painterResource, contentDescription = "")
            },
            isError = errorState
        )
    }
}

/*****************************************************************************
        * Used in Login, registration and other data interaction Screen *

    Let the user enter some password-related information
 *****************************************************************************/
@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    painterResource : Painter,
    onTextSelected: (String) -> Unit,
    errorState : Boolean = false,
    startRange : Dp = 25.dp,
    endRange : Dp = 25.dp
) {
    val localFocusManager = LocalFocusManager.current
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember{ mutableStateOf(false)}

    Column {
        Text(
            text = "* $labelValue",
            color = MenuRowFont,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = startRange, top = 2.dp)
                .align(Alignment.Start)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .padding(start = startRange, end = endRange),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                cursorColor = Color.White,
                backgroundColor = GreyWhite
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            keyboardActions = KeyboardActions { localFocusManager.clearFocus() },
            maxLines = 1,
            value = password.value,
            onValueChange = {
                password.value = it
                onTextSelected(it)
            },
            leadingIcon = {
                Icon(painter = painterResource, contentDescription = "")
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = painterResource(id = R.drawable.visible),
                        contentDescription = ""
                    )
                }
            },
            visualTransformation = if (passwordVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = errorState
        )
    }
}

/*****************************************************************************
                        * Used in almost Screen *

    Display Common Button
 *****************************************************************************/
@Composable
fun ButtonComponent(
    value: String,
    onButtonClick : () -> Unit,
    startRange : Dp = 50.dp,
    endRange : Dp = 50.dp
){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .padding(start = startRange, end = endRange),
        onClick = {
            onButtonClick.invoke()
        },
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    color = ButtonGreen,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Text(text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
        }
    }
}


/*****************************************************************************
    * Used in Login, registration and other data interaction Screen *

    Display error messages for user modification
 *****************************************************************************/
@Composable
fun ErrorMessageComponent(
    errorText: String,
    startRange : Dp = 25.dp,
    endRange : Dp = 25.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .padding(start = startRange, end = endRange),
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error icon",
            tint = Color.Red,
            modifier = Modifier
                .size(14.dp)
                .padding(end = 4.dp)
        )

        Text(
            text = errorText,
            color = Color.Red,
            fontSize = 14.sp
        )
    }
}

/*****************************************************************************
                    * Used in Profile type Screen *

    Use as a click bar to jump to a subpage.
 *****************************************************************************/

@Composable
fun MenuRow(menuItem: MenuItem, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .height(70.dp)
            .background(color = MenuRowGrey)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = menuItem.icon),
            contentDescription = null,
            tint = MenuRowFont,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = menuItem.title,
            fontWeight = FontWeight.Bold,
            color = MenuRowFont,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

/*****************************************************************************
                        * Used in AccountScreen *

    Used to display the user's name, e-mail address and image.
 *****************************************************************************/
@Composable
fun UserInformation(
    userName: String,
    userEmail: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(120.dp),
        color = MenuRowGrey
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.android_symbol_green_rgb),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = userName,
                    color = MenuRowFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = userEmail,
                    color = MenuRowFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

/*****************************************************************************
                    * Used in Profile type Screen *

    Split line
 *****************************************************************************/
@Composable
fun PartialLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = MenuRowGrey)
    ) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 64.dp)
        ) {
            drawLine(
                color = GreyWhite,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = size.width, y = 0f),
                strokeWidth = 1f
            )
        }
    }
}

/*****************************************************************************
                    * Used in most SubScreen *

    Used to design each button in the recharge system
*****************************************************************************/
@Composable
fun TopReturnBar(title: String, onButtonClick: () -> Unit) {
    TopAppBar(
        backgroundColor = MenuRowGrey,
        elevation = 4.dp,
        modifier = Modifier.height(70.dp)
    ) {
        IconButton(onClick = onButtonClick) {
            Icon(Icons.Default.ArrowBack, tint = MenuRowFont, contentDescription = "back")
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MenuRowFont,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(Modifier.weight(1f))


        IconButton(onClick = {}, modifier = Modifier.alpha(0f)) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }
    }
}

/*****************************************************************************
                        * Use in PurchaseScreen *

    Used to design each button in the recharge system
 *****************************************************************************/

@Composable
private fun PaymentButtonComponent(
    value: String,
    onButtonClick : () -> Unit,
    startRange : Dp = 20.dp,
    endRange : Dp = 20.dp,
    height : Dp,
    width : Dp,
) {
    Button(
        modifier = Modifier
            .heightIn(height)
            .width(width)
            .padding(start = startRange, end = endRange),
        onClick = { onButtonClick.invoke() },
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ButtonGreen,
            contentColor = ButtonWhite
        )
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/*****************************************************************************
                        * Use in PurchaseScreen *

    Show all options for Recharge, 5$, 10$, 20$, 50$, 100$, 200$
 *****************************************************************************/
@Composable
private fun RechargeButtonGroup(navController: NavController) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(BackGround),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentButtonComponent(
                    value = stringResource(id = R.string.dollar5),
                    onButtonClick = {
                        navController.navigate("payment/5.00")
                    },
                    height = 60.dp,
                    width = 170.dp
                )

                Spacer(modifier = Modifier.width(10.dp))

                PaymentButtonComponent(
                    value = stringResource(id = R.string.dollar10),
                    onButtonClick = {
                        navController.navigate("payment/10.00")
                    },
                    height = 60.dp,
                    width = 170.dp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentButtonComponent(
                    value = stringResource(id = R.string.dollar20),
                    onButtonClick = {
                        navController.navigate("payment/20.00")
                    },
                    height = 60.dp,
                    width = 170.dp
                )

                Spacer(modifier = Modifier.width(10.dp))

                PaymentButtonComponent(
                    value = stringResource(id = R.string.dollar50),
                    onButtonClick = {
                        navController.navigate("payment/50.00")
                    },
                    height = 60.dp,
                    width = 170.dp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentButtonComponent(
                    value = stringResource(id = R.string.dollar100),
                    onButtonClick = {
                        navController.navigate("payment/100.00")
                    },
                    height = 60.dp,
                    width = 170.dp
                )

                Spacer(modifier = Modifier.width(10.dp))

                PaymentButtonComponent(
                    value = stringResource(id = R.string.dollar200),
                    onButtonClick = {
                        navController.navigate("payment/200.00")
                    },
                    height = 60.dp,
                    width = 170.dp
                )
            }
        }
    }
}


/*****************************************************************************
                        * Use in PurchaseScreen *

    Show all options for Pass, 1-Day Pass, 7-Day Pass, 31-Day Pass
 *****************************************************************************/

@Composable
private fun PassButtonGroup(navController: NavController) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(BackGround),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentButtonComponent(
                    value = stringResource(id = R.string.day_pass),
                    onButtonClick = {
                        navController.navigate("payment/11.00")
                    },
                    height = 60.dp,
                    width = 310.dp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentButtonComponent(
                    value = stringResource(id = R.string.weekly_pass),
                    onButtonClick = {
                        navController.navigate("payment/22.50")
                    },
                    height = 60.dp,
                    width = 310.dp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                PaymentButtonComponent(
                    value = stringResource(id = R.string.monthly_pass),
                    onButtonClick = {
                        navController.navigate("payment/90.00")
                    },
                    height = 60.dp,
                    width = 310.dp
                )
            }
        }
    }
}

/*****************************************************************************
                            * Use in PurchaseScreen *

    A set of alternating buttons, used to allow the user to choose between
    recharging the amount or purchasing a Pass.
 *****************************************************************************/
@Composable
private fun SlideBar(selectedOption: String, onMoneyClick: () -> Unit, onPassClick: () -> Unit) {
    val moneyButtonColor = if (selectedOption == "money") ButtonGreen else BottomBarGrey
    val passButtonColor = if (selectedOption == "pass") ButtonGreen else BottomBarGrey

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onMoneyClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = moneyButtonColor,
                contentColor = ButtonWhite
            ),
            modifier = Modifier
                .heightIn(50.dp)
                .width(160.dp)
                .padding(start = 20.dp, end = 20.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = "Money",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Button(
            onClick = onPassClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = passButtonColor,
                contentColor = ButtonWhite
            ),
            modifier = Modifier
                .heightIn(50.dp)
                .width(160.dp)
                .padding(start = 20.dp, end = 20.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = "Pass",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ContentDisplay(displayState: String, navController: NavController) {
    when (displayState) {
        "money" -> RechargeButtonGroup(navController)
        "pass" -> PassButtonGroup(navController)
    }
}

/*****************************************************************************
                        * Use in PurchaseScreen *

    Show all buttons for the entire recharge function
 *****************************************************************************/

@Composable
fun PurchaseSystem(navController: NavController) {
    var selectedOption by remember { mutableStateOf("money") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround)
    ) {
        HeadingTextComponent("Recharge or Pass", MenuRowFont, 25.sp)
        SlideBar(
            selectedOption = selectedOption,
            onMoneyClick = { selectedOption = "money" },
            onPassClick = { selectedOption = "pass" }
        )
        Spacer(modifier = Modifier.heightIn(40.dp))
        HeadingTextComponent("Purchase Options", MenuRowFont, 25.sp)
        ContentDisplay(selectedOption, navController)
    }
}

/*****************************************************************************
                        * Use in PaymentScreen *

    Let the user select the valid expiration date by sliding the selection bar
 *****************************************************************************/
@Composable
private fun DropdownMenuSpinner(
    selectedItem: String,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    menuHeight: Dp = 260.dp
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(GreyWhite)
            .width(86.dp)
            .height(50.dp)
            .clickable { expanded = true }
    ) {
        Text(
            text = selectedItem,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
                .padding(end = 16.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(86.dp)
                .heightIn(max = menuHeight)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                ) {
                    Text(text = item)
                }
            }
        }
    }
}

@Composable
fun ExpirationDatePicker(
    onExpiryMonthChanged: (String) -> Unit,
    onExpiryYearChanged: (String) -> Unit,
    startRange : Dp = 25.dp,
) {
    val months = (1..12).map { it.toString().padStart(2, '0') }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear until currentYear + 20).map { it.toString() }

    val expiryMonth = remember {
        mutableStateOf("")
    }
    val  expiryYear = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(start = startRange, top = 2.dp)) {
        Text(
            text = "* ${stringResource(id = R.string.expirationDate)}",
            color = MenuRowFont,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 2.dp)
                .align(Alignment.Start)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownMenuSpinner(
                selectedItem = expiryMonth.value,
                items = months,
                onItemSelected = { selectedMonth ->
                    expiryMonth.value = selectedMonth
                    onExpiryMonthChanged(expiryMonth.value)
                }
            )
            Spacer(modifier = Modifier.widthIn(5.dp))
            DropdownMenuSpinner(
                selectedItem = expiryYear.value,
                items = years,
                onItemSelected = { selectedYear ->
                    expiryYear.value = selectedYear
                    onExpiryYearChanged(expiryYear.value)
                }
            )
        }
    }
}

@Composable
fun Cvv(
    labelValue : String,
    onTextSelected : (String) -> Unit,
    errorState : Boolean = false,
) {
    val textValue = remember {
        mutableStateOf("")
    }
    Column(Modifier.padding( top = 2.dp)) {
        Text(
            text = "* $labelValue",
            color = MenuRowFont,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 2.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .width(160.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(4.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                cursorColor = Color.White,
                backgroundColor = GreyWhite
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onTextSelected(it)
            },
            isError = errorState
        )
    }
}

/*****************************************************************************
                            * Use in CardScreen *

    Display of public transportation cards

    Need to design UI
    TODO!!!
 *****************************************************************************/
@Composable
fun TransportationCard(cardNumber : String = "") {
    Card(
        elevation = 6.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(20.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.transportation_card_background),
                contentDescription = "Transportation Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Boston Transportation Card",
                color = GreyWhite,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 14.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(6f, 6f),
                        blurRadius = 6f
                    )
                ),
            )

            Text(
                text = cardNumber,
                color = GreyWhite,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 32.dp, bottom = 16.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(6f, 6f),
                        blurRadius = 6f
                    )
                ),
            )
        }
    }
}

/*****************************************************************************
                        * Use in CardScreen

    Display information about the user's intelligent public transportation card
 *****************************************************************************/
@Composable
fun AccountInfo(title : String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MenuRowFont,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            )
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = value,
            color = MenuRowFont,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            )
        )
    }
}

@Composable
fun AccountInformationComponents(balance: String, passDueDate: String ){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal =  20.dp),
    ) {
        AccountInfo("Balance: ", "$ $balance")
        AccountInfo("Pass Expiration Date：", passDueDate)
    }
}

/*****************************************************************************
                    * Use in PurchaseHistoryScreen *

    Used to record all reload records (amount or Pass) of an account.
 *****************************************************************************/
@Composable
fun PurchaseHistoryItem(rechargeTime: String, rechargeAction: String, currentBalance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(0.7f)
            ) {
                Text(
                    text = rechargeAction,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Transaction Time: $rechargeTime",
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                )
            }

            Text(
                modifier = Modifier.weight(0.3f),
                text = "Balance: $$currentBalance",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun PurchaseHistoryList(purchaseHistory: List<RechargeRecord>) {
    LazyColumn {
        items(purchaseHistory) { record ->
            PurchaseHistoryItem(
                rechargeTime = record.rechargeTime,
                rechargeAction = record.rechargeAction,
                currentBalance = record.currentBalance
            )
        }
    }
}


/*****************************************************************************
                        * Use in RideHistoryScreen *

    Used to present all the records of the user's rides on the transportation system
 *****************************************************************************/
@Composable
fun RideHistoryItem(ridingTime: String, ridingAction: String,currentBalance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(0.7f)
            ) {
                Text(
                    text = ridingAction,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Transaction Time: $ridingTime",
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                )
            }

            Text(
                modifier = Modifier.weight(0.3f),
                text = "Balance: $$currentBalance",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun RideHistoryList(ridingHistory: List<SpendingRecord>) {
    LazyColumn {
        items(ridingHistory) { record ->
            RideHistoryItem(
                ridingTime = record.ridingTime,
                ridingAction = record.deductionAction,
                currentBalance = record.currentBalance
            )
        }
    }
}


/*****************************************************************************
                            * Use in PayScreen *

    Used to generate a QR code as a payment way

    Currently not available, only for display!!!
    TODO!!!
 *****************************************************************************/
@Composable
private fun ShowQRCode(qrCodeBitmap: Bitmap) {
    val imageBitmap = qrCodeBitmap.asImageBitmap()
    Image(
        bitmap = imageBitmap,
        contentDescription = "QR Code",
        modifier = Modifier.wrapContentSize()
    )
}

private fun generateQRCode(content: String): Bitmap {
    val size = 900
    val hints = mapOf(
        EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,
        EncodeHintType.MARGIN to 0
    )
    val bitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }
    return bmp
}

@Composable
fun QRFunction(content: String) {
    val qrCodeBitmap = generateQRCode(content)
    ShowQRCode(qrCodeBitmap)
}