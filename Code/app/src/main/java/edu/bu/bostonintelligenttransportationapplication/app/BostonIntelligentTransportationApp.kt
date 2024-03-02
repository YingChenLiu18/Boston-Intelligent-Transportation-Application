package edu.bu.bostonintelligenttransportationapplication.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.data.navigation.BottomNavItem
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.profile_Modules.EditNameScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.forgotPassword_Modules.ForgotPasswordScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.forgotPassword_Modules.EmailSentScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.payment_Modules.FailureToPayScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.payment_Modules.PaymentScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.history_Modules.PurchaseHistoryScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.profile_Modules.ResetPasswordScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.history_Modules.RidingHistoryScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.payment_Modules.SuccessfulPaymentScreen
import edu.bu.bostonintelligenttransportationapplication.screen.access_Modules.LoginScreen
import edu.bu.bostonintelligenttransportationapplication.screen.access_Modules.PrivacyPolicyScreen
import edu.bu.bostonintelligenttransportationapplication.screen.access_Modules.RegisterScreen
import edu.bu.bostonintelligenttransportationapplication.screen.access_Modules.HomeScreen
import edu.bu.bostonintelligenttransportationapplication.screen.access_Modules.TermsAndConditionsScreen
import edu.bu.bostonintelligenttransportationapplication.screen.main_Modules.AccountScreen
import edu.bu.bostonintelligenttransportationapplication.screen.main_Modules.CardScreen
import edu.bu.bostonintelligenttransportationapplication.screen.additional_Modules.account_Modules.profile_Modules.MyProfileScreen
import edu.bu.bostonintelligenttransportationapplication.screen.main_Modules.PayScreen
import edu.bu.bostonintelligenttransportationapplication.screen.main_Modules.PurchaseScreen
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BottomBarGrey
import edu.bu.bostonintelligenttransportationapplication.ui.theme.ButtonGreen
import edu.bu.bostonintelligenttransportationapplication.ui.theme.ButtonWhite

@Composable
fun BostonIntelligentTransportationApp() {
    val navController = rememberNavController()
    val shouldShowBottomBar = navController.currentBackStackEntryAsState().value?.destination?.route in listOf("pay", "card", "purchase", "account")

    Scaffold(
        bottomBar = { if (shouldShowBottomBar) BottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavHost(navController = navController)
        }
    }

}

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }

        composable("register") { RegisterScreen(navController) }
        composable("termsAndConditions") { TermsAndConditionsScreen(navController) }
        composable("privacyPolicy") { PrivacyPolicyScreen(navController) }

        composable("login") { LoginScreen(navController) }
        composable("forgotPassword") { ForgotPasswordScreen(navController) }
        composable("emailSent") { EmailSentScreen(navController) }

        composable("pay") { PayScreen(navController)}
        composable("failureToPay") { FailureToPayScreen(navController) }

        composable("card") { CardScreen() }

        composable("purchase") { PurchaseScreen(navController) }
        composable("payment/{price}"){ backStackEntry ->
            val price = backStackEntry.arguments?.getString("price") ?: "0.00"
            PaymentScreen(navController, price)
        }
        composable("successfulPayment"){ SuccessfulPaymentScreen(navController) }

        composable("account") { AccountScreen(navController) }
        composable("myProfile"){ MyProfileScreen(navController) }
        composable("resetPassword"){ ResetPasswordScreen(navController) }
        composable("editName"){ EditNameScreen(navController) }
        composable("purchaseHistory"){ PurchaseHistoryScreen(navController) }
        composable("ridingHistory"){ RidingHistoryScreen(navController) }

    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavItem.Pay,
        BottomNavItem.Card,
        BottomNavItem.Purchase,
        BottomNavItem.Account
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    BottomNavigation(
        backgroundColor = BottomBarGrey,
        contentColor = ButtonWhite
    ) {
        screens.forEach { screen ->
            BottomNavigationItem(
                label = { Text(screen.title) },
                icon = { Icon(screen.icon, contentDescription = null) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    if (navController.currentDestination?.route != screen.route) {
                        navController.navigate(screen.route)
                    }
                },
                unselectedContentColor = ButtonWhite,
                selectedContentColor = ButtonGreen
            )
        }
    }
}
