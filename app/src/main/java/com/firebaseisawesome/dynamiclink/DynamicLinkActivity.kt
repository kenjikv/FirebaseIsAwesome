package com.firebaseisawesome.dynamiclink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebaseisawesome.R
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.DynamicLink.*
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import kotlinx.android.synthetic.main.activity_dynamic_link.*


class DynamicLinkActivity : AppCompatActivity() {

    lateinit var dynamicLink: FirebaseDynamicLinks;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_link)

        dynamicLink = FirebaseDynamicLinks.getInstance()

        getDynamicLink();
    }

    private fun getDynamicLink() {
        dynamicLink
            .getDynamicLink(intent)
            .addOnSuccessListener {pendingDynamicLinkData ->

                var deeplink: Uri
                if(pendingDynamicLinkData != null){
                    deeplink = pendingDynamicLinkData.link!!
                    if(deeplink.getQueryParameter("mivalor") != null){
                        dynamicTvText.text = deeplink.getQueryParameter("mivalor")
                    }
                }
            }
            .addOnFailureListener{
                Toast.makeText(this@DynamicLinkActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    fun onClickDynamicLink(view: View) {
        var mivalor = "Kenji Kawaida";

        FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse("https://firebaseisawesome.page.link?mivalor=$mivalor"))
            .setDomainUriPrefix("https://firebaseisawesome.page.link")
            .setAndroidParameters(AndroidParameters.Builder("com.firebaseisawesome.dynamiclink")
                .setMinimumVersion(1)
                .build())
            .setIosParameters(IosParameters.Builder("com.firebaseisawesome.dynamiclink")
                .setAppStoreId("whatever")
                .setMinimumVersion("1.0.1")
                .build())
            .setSocialMetaTagParameters(SocialMetaTagParameters.Builder()
                .setTitle("Firebase Is Awesome")
                .setDescription("Esta es una prueba de DynamicLink")
                .setImageUrl(Uri.parse("https://www.android.com/static/images/logos/andy-lg.png"))
                .build())
            .buildShortDynamicLink()
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    var shortLink = task.result.shortLink
                    var flowLink = task.result.previewLink
                    var sendIntent = Intent()
                    sendIntent.setAction(Intent.ACTION_SEND)
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Compartiendo mi link de prueba: ${shortLink.toString()}")
                    sendIntent.setType("text/plain")

                    var shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent)
                }else{
                    Toast.makeText(this@DynamicLinkActivity, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }

    }
}