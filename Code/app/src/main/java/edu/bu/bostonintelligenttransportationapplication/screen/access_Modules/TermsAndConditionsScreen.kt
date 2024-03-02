package edu.bu.bostonintelligenttransportationapplication.screen.access_Modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.bu.bostonintelligenttransportationapplication.components.CommonTextComponents
import edu.bu.bostonintelligenttransportationapplication.components.TopReturnBar
import edu.bu.bostonintelligenttransportationapplication.ui.theme.BackGround

@Composable
fun TermsAndConditionsScreen(navController: NavController){
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
                TopReturnBar(title = "Term of Use", onButtonClick = { navController.navigate("register") })
                Spacer(modifier = Modifier.heightIn(20.dp))
                TermsAndConditionsText()
            }
        }
    }
}

@Composable
fun TermsAndConditionsText() {
    LazyColumn {
        item {
            CommonTextComponents("Last Updated: [Oct,27th,2023]", 16.sp, FontWeight.Bold)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("1. Acceptance of Terms", 16.sp, FontWeight.Bold)
            CommonTextComponents("By accessing and using this website or application (Boston Intelligent Transportation Application), " +
                    "you acknowledge that you have read, understood, " +
                    "and agree to be bound by these Terms of Use and the Privacy Policy. " +
                    "If you do not accept these terms, please do not use this Platform.", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("2. Changes to Terms", 16.sp, FontWeight.Bold)
            CommonTextComponents("We reserve the right, at our discretion, to change, modify, add, or remove portions of these terms at any time. " +
                    "Please check these terms periodically for changes. " +
                    "Your continued use of this Platform following the posting of changes to these terms will mean you accept those changes.", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("3. Use of Platform", 16.sp, FontWeight.Bold)
            CommonTextComponents("This Platform is intended for personal and non-commercial use. You may not modify, copy, distribute, transmit, display, perform, reproduce, publish, " +
                    "license, create derivative works from, transfer, or sell any information, software, products, or services obtained from this Platform.", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("4. User Conduct", 16.sp, FontWeight.Bold)
            CommonTextComponents("You agree to use this Platform only for lawful purposes. " +
                    "You agree not to take any action that might compromise the security of the Platform, " +
                    "render the Platform inaccessible to others, or otherwise cause damage to the Platform or its content.", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("5. Limitation of Liability", 16.sp, FontWeight.Bold)
            CommonTextComponents("We shall not be liable for any damages or injury resulting from your access to, or inability to access, this Platform, or from any virus, bugs, " +
                    "tampering, omission, interruption, deletion, or any other error.", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("6. Governing Law", 16.sp, FontWeight.Bold)
            CommonTextComponents("These terms and conditions shall be governed by and construed in accordance with Massachusetts State Law. " +
                    "Any disputes arising under or in connection with these Terms of Use shall be subject to the exclusive jurisdiction of the Massachusetts State Law.", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("7. Third-party Links", 16.sp, FontWeight.Bold)
            CommonTextComponents("This Platform may contain links to third-party websites which are not controlled by us. We are not responsible for the content of any linked site or any link contained in a linked site.", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("8. Termination", 16.sp, FontWeight.Bold)
            CommonTextComponents("We reserve the right, in our sole discretion, to terminate your access to all or part of this Platform, with or without notice.", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("9. Feedback and Complaints  ", 16.sp, FontWeight.Bold)
            CommonTextComponents("If you have any feedback or complaints about this Platform, please contact us at [lycliu@bu.edu].", 14.sp, FontWeight.Normal)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("10. Miscellaneous", 16.sp, FontWeight.Bold)
            CommonTextComponents("If any provision of these terms is found to be unlawful, void, or unenforceable, then that provision shall be deemed severable from these terms and shall not affect the validity and enforceability of any remaining provisions.", 14.sp, FontWeight.Normal)
        }
    }
}

@Preview
@Composable
fun TermsAndConditionsScreenPreview(){
    val navController = rememberNavController()
    TermsAndConditionsScreen(navController = navController)
}
