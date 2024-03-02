package edu.bu.bostonintelligenttransportationapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import edu.bu.bostonintelligenttransportationapplication.app.BostonIntelligentTransportationApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BostonIntelligentTransportationApp()
        }
    }
}


