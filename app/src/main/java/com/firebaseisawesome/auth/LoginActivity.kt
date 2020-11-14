package com.firebaseisawesome.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.firebaseisawesome.R
import com.firebaseisawesome.ui.activity.BaseActivity
import com.firebaseisawesome.ui.activity.MainActivity
import com.firebaseisawesome.ui.dialog.DialogText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
    }

    fun onClickLogin(view: View) {
        var mail = etLoginMail.text.toString()
        var pass = etLoginPassword.text.toString()

        hideKeyboard(etLoginPassword)
        showProgressDialog("Loading...");

        mAuth.signInWithEmailAndPassword(mail, pass)
            .addOnCompleteListener { task ->

                hideProgressDialog()

                if (task.isSuccessful) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    showDialog("Login", task.exception.toString())
                }
            }.addOnFailureListener { e->
                showDialog("Login", e.toString())
            }

    }

    fun onClickRegister(view: View) {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }

    fun onClickPassword(view: View) {
        var dialog = DialogText(this, DialogText.OnDialogFinish {text ->
            if (text.trim { it <= ' ' }.isEmpty()) {
                showDialog("Recuperarción de contraseña", "Correo invalido")
                return@OnDialogFinish
            }

            showProgressDialog("Loading...")

            mAuth.sendPasswordResetEmail(text).addOnCompleteListener{task ->
                hideProgressDialog()
                if(task.isSuccessful){
                    showDialog("Recuperarción de contraseña", "Se ha enviado un correo electronico a su bandeja de mensajes.")
                }else{
                    showDialog("Recuperarción de contraseña", "Este correo no esta registrado.")
                }
            }.addOnFailureListener{e->
                hideProgressDialog()
                showDialog("Recuperarción de contraseña", e.toString())
            }
        })

        dialog.setTitle("Recuperar contraseña")
        dialog.setTextButton("Recuperar")
        dialog.show()
    }
}