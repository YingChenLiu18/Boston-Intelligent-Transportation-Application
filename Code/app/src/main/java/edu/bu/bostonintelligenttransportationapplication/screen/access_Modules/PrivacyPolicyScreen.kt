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
fun PrivacyPolicyScreen(navController: NavController){
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
                TopReturnBar(title = "Privacy Policy", onButtonClick = { navController.navigate("register") })
                Spacer(modifier = Modifier.heightIn(20.dp))
                PrivacyPolicyText()
            }
        }
    }
}

@Composable
fun PrivacyPolicyText() {
    LazyColumn {
        item {
            CommonTextComponents("Last Updated: [Oct,27th,2023]", 16.sp, FontWeight.Bold)
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents(
                "Welcome to Boston Intelligent Transportation Application! " +
                        "We value your privacy and are committed to protecting your personal data. " +
                        "This privacy policy aims to help you understand how we collect, use, store, and share your information.",
                14.sp, FontWeight.Normal
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("1. Information Collection", 16.sp, FontWeight.Bold)
            CommonTextComponents(
                "We may collect the following types of information:",
                14.sp,
                FontWeight.Normal
            )
            CommonTextComponents(
                "Information provided during account registration, such as email, username, etc.",
                14.sp,
                FontWeight.Normal
            )
            CommonTextComponents(
                "Device information, browser type, IP address, etc. when using our services.",
                14.sp,
                FontWeight.Normal
            )
            CommonTextComponents(
                "Information received when logging in via third-party services.",
                14.sp,
                FontWeight.Normal
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("2. Information Use", 16.sp, FontWeight.Bold)
            CommonTextComponents("We may use your information to:", 14.sp, FontWeight.Normal)
            CommonTextComponents("Provide, maintain, and improve our services.", 14.sp, FontWeight.Normal)
            CommonTextComponents("Provide customer support to you.", 14.sp, FontWeight.Normal)
            CommonTextComponents(
                "Send updates, security alerts, or other information related to our services.",
                14.sp,
                FontWeight.Normal
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("3. Information Sharing", 16.sp, FontWeight.Bold)
            CommonTextComponents(
                "We do not sell your personal data. However, we might share your information with third parties under the following circumstances:",
                14.sp,
                FontWeight.Normal
            )
            CommonTextComponents("With your consent.", 14.sp, FontWeight.Normal)
            CommonTextComponents(
                "As required by law or in response to a legal request.",
                14.sp,
                FontWeight.Normal
            )
            CommonTextComponents(
                "With partners to provide services, but they are bound to uphold confidentiality.",
                14.sp,
                FontWeight.Normal
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("4. Information Security", 16.sp, FontWeight.Bold)
            CommonTextComponents(
                "We utilize various measures to protect your data against unauthorized access, use, or disclosure.",
                14.sp,
                FontWeight.Normal
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("5. Third-party Services", 16.sp, FontWeight.Bold)
            CommonTextComponents(
                "Our services might include links to, services of, or plug-ins from third parties. " +
                        "This privacy policy does not cover the information practices of those third parties, " +
                        "so please review their privacy policies for details.",
                14.sp,
                FontWeight.Normal
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("6. Changes", 16.sp, FontWeight.Bold)
            CommonTextComponents(
                "We might update this privacy policy from time to time. Significant changes will be notified on our website or app.",
                14.sp,
                FontWeight.Normal
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            CommonTextComponents("7. Contact Us", 16.sp, FontWeight.Bold)
            CommonTextComponents(
                "If you have any questions about this privacy policy, please contact us at lycliu@bu.edu",
                14.sp,
                FontWeight.Normal
            )
        }
    }
}
@Preview
@Composable
fun PrivacyPolicyScreenPreview(){
    val navController = rememberNavController()
    PrivacyPolicyScreen(navController)
}