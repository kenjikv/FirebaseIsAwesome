package com.firebaseisawesome.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebaseisawesome.R
import com.firebaseisawesome.ui.activity.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
    }

    fun onClickRegister(view: View) {
        var mail = etRegisterMail.text.toString()
        var password = etRegisterPassword.text.toString()

        if (mail.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(
                this, "Su correo o contraseña no puede estar vacia.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this, task.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


}