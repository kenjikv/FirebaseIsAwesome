package com.firebaseisawesome.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.firebaseisawesome.R
import com.firebaseisawesome.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initLoadingUI()
        initVerifyUser()
    }

    private fun initVerifyUser() {
        mAuth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            var currentUser = mAuth.currentUser

            if (currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else{
                startActivity(Intent(this, LoginActivity::class.java))
            }

            finish()
        }, 3000)
    }

    private fun initLoadingUI() {
        val image: ImageView = findViewById(R.id.ivLoading)
        Glide.with(this).asGif().load(R.raw.loading).into(image)
    }
}