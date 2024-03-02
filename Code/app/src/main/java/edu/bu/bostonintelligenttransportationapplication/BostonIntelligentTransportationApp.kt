package edu.bu.bostonintelligenttransportationapplication

import android.app.Application
import com.google.firebase.FirebaseApp

class BostonIntelligentTransportationApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}