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
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_bar_code.*

class BarCodeActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code)
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
            imageBarcode.setImageBitmap(bitmap)
            searchLabelsInImage(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun searchLabelsInImage(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().visionBarcodeDetector

        detector.detectInImage(image)
            .addOnSuccessListener { barcodes ->
                val builder = StringBuilder()
                for (barcode in barcodes){
                    val valueType = barcode.valueType
                    when (valueType) {
                        FirebaseVisionBarcode.TYPE_WIFI -> {
                            builder.append("ssid: ${barcode.wifi!!.ssid}").append("\n")
                            builder.append("password: ${barcode.wifi!!.password}").append("\n")
                            builder.append("type: ${barcode.wifi!!.encryptionType}").append("\n")
                        }
                        FirebaseVisionBarcode.TYPE_URL -> {
                            builder.append("title: ${barcode.url!!.title}").append("\n")
                            builder.append("url: ${barcode.url!!.url}").append("\n")
                        }
                    }


                }
                txtBarcode.text = builder.toString()
            }
            .addOnFailureListener{ e->
                Log.e("MLActivity", "Error labels", e)
            }
    }
}