package com.firebaseisawesome.crashlytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.firebaseisawesome.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)

        mAuth = FirebaseAuth.getInstance()

        mAuth.currentUser?.uid?.let { FirebaseCrashlytics.getInstance().setCustomKey("uid", it) }
        mAuth.currentUser?.email?.let { FirebaseCrashlytics.getInstance().setCustomKey("email", it) }
    }

    fun onClickCrash(view: View) {
        FirebaseCrashlytics.getInstance().log("Se ha presionado en el boton onClickCrash")
        throw RuntimeException("Test Crash")
    }
}