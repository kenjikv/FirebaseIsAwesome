package com.firebaseisawesome.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.firebaseisawesome.R
import com.firebaseisawesome.auth.LoginActivity
import com.firebaseisawesome.crashlytics.CrashActivity
import com.firebaseisawesome.dynamiclink.DynamicLinkActivity
import com.firebaseisawesome.firestore.FirestoreActivity
import com.firebaseisawesome.ml.MLActivity
import com.firebaseisawesome.remoteconfig.RemoteActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
    }

    fun onClickFirestore(view: View) {
        startActivity(Intent(this, FirestoreActivity::class.java))
    }

    fun onClickCrashlytics(view: View) {
        startActivity(Intent(this, CrashActivity::class.java))
    }

    fun onClickRemoteConfig(view: View) {
        startActivity(Intent(this, RemoteActivity::class.java))
    }

    fun onClickDynamicLink(view: View) {
        startActivity(Intent(this, DynamicLinkActivity::class.java))
    }

    fun onClickMLKit(view: View) {
        startActivity(Intent(this, MLActivity::class.java))
    }

    fun onClickSignOut(view: View) {
        mAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


}