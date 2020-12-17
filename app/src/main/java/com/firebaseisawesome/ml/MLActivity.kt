package com.firebaseisawesome.ml

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.firebaseisawesome.R

class MLActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_l)
    }

    fun onClickText(view: View) {
        startActivity(Intent(this, TextRecognitionActivity::class.java))
    }

    fun onClickImageLabel(view: View) {
        startActivity(Intent(this, ImageLabelActivity::class.java))
    }

    fun onClickBarcode(view: View) {
        startActivity(Intent(this, BarCodeActivity::class.java))
    }


}