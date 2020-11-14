package com.firebaseisawesome.ui.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.firebaseisawesome.R

open class BaseActivity: AppCompatActivity() {

    var mProgressDialog: Dialog? = null

    fun showProgressDialog(message: String?) {
        if (mProgressDialog == null) {
            mProgressDialog = Dialog(this)
            mProgressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mProgressDialog!!.setContentView(R.layout.layout_progress)
            mProgressDialog!!.setCancelable(false)
            if (message != null && !message.isEmpty()) {
                val text = mProgressDialog!!.findViewById<TextView>(R.id.textView1)
                text.text = message
            }
            val image =
                mProgressDialog!!.findViewById<ImageView>(R.id.image)
            Glide.with(this).asGif().load(R.raw.loading).into(image)
        }
        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    fun hideKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showDialog(title: String?, message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setNeutralButton("Done", null)
        builder.show()
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

}