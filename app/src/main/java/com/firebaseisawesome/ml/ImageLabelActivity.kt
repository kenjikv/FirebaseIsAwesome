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
import kotlinx.android.synthetic.main.activity_image_label.*
import java.lang.StringBuilder

class ImageLabelActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_label)
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
            imageLabeling.setImageBitmap(bitmap)
            searchLabelsInImage(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun searchLabelsInImage(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().onDeviceImageLabeler

        detector.processImage(image)
            .addOnSuccessListener { labels ->
                val builder = StringBuilder()
                for (label in labels){
                    builder.append(label.text).append(" (${label.confidence})").append("\n")
                }
                txtLabels.text = builder.toString()
            }
            .addOnFailureListener{ e->
                Log.e("MLActivity", "Error labels", e)
            }
    }
}