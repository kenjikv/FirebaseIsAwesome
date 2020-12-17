package com.firebaseisawesome.ml

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.firebaseisawesome.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_m_l.*

class MLActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    val ML_TEXT_RECOGNITION = 1
    val ML_IMAGE_LABELS = 2
    val ML_BARCODE = 3
    var type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_l)
    }

    fun onClickText(view: View) {
        this.type = ML_TEXT_RECOGNITION
        takePhoto()
    }

    fun onClickImageLabel(view: View) {
        this.type = ML_IMAGE_LABELS
        takePhoto()
    }

    fun onClickBarcode(view: View) {
        this.type = ML_BARCODE
        takePhoto()
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            ivImageMl.setImageBitmap(bitmap)

            when (type) {
                ML_TEXT_RECOGNITION -> {
                    searchTextInImage(bitmap)
                }
                ML_IMAGE_LABELS -> {
                    searchLabelsInImage(bitmap)
                }
                ML_BARCODE -> {
                    searchBarcodeInImage(bitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun searchTextInImage(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener { result ->
                val builder = StringBuilder()
                for (block in result.textBlocks) {
                    builder.append(block.text).append(" ")
                }
                tvLabelsMl.text = builder.toString()
            }
            .addOnFailureListener { e ->
                Log.e("MLActivity", "Error text", e)
            }
    }

    private fun searchLabelsInImage(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().onDeviceImageLabeler

        detector.processImage(image)
            .addOnSuccessListener { labels ->
                val builder = StringBuilder()
                for (label in labels) {
                    builder.append(label.text).append(" (${label.confidence})").append("\n")
                }
                tvLabelsMl.text = builder.toString()
            }
            .addOnFailureListener { e ->
                Log.e("MLActivity", "Error labels", e)
            }
    }

    private fun searchBarcodeInImage(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().visionBarcodeDetector

        detector.detectInImage(image)
            .addOnSuccessListener { barcodes ->
                val builder = StringBuilder()
                for (barcode in barcodes) {
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
                tvLabelsMl.text = builder.toString()
            }
            .addOnFailureListener { e ->
                Log.e("MLActivity", "Error labels", e)
            }
    }

}