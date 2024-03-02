package edu.bu.bostonintelligenttransportationapplication.data.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
){
    object Pay : BottomNavItem(
        title = "Pay",
        route = "pay",
        icon = Icons.Default.QrCodeScanner
    )
    object Card : BottomNavItem(
        title = "Card",
        route = "card",
        icon = Icons.Default.CreditCard
    )
    object Purchase : BottomNavItem(
        title = "Purchase",
        route = "purchase",
        icon = Icons.Default.ShoppingCart
    )
    object Account : BottomNavItem(
        title = "Account",
        route = "account",
        icon = Icons.Default.AccountCircle
    )
}
