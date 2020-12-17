package com.firebaseisawesome.ml

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.firebaseisawesome.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_m_l.*
import kotlinx.android.synthetic.main.activity_text_recognition.*
import java.lang.StringBuilder

class TextRecognitionActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recognition)
    }

    fun onClickTakePhoto(view: View) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{ takePictureIntent->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap = data?.extras?.get("data") as Bitmap
            imageText.setImageBitmap(bitmap)
            searchLabelsInImage(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun searchLabelsInImage(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener { result ->
                val builder = StringBuilder()
                for (block in result.textBlocks){
                    builder.append(block.text).append(" ")
                }
                txtTextLabels.text = builder.toString()
            }
            .addOnFailureListener{ e->
                Log.e("MLActivity", "Error text", e)
            }
    }
}