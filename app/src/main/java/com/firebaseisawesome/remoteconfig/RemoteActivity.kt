package com.firebaseisawesome.remoteconfig

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebaseisawesome.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.android.synthetic.main.activity_remote.*


class RemoteActivity : AppCompatActivity() {

    lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)

        remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings{
            minimumFetchIntervalInSeconds = 10
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    updateUI()
                }
            }
    }

    private fun updateUI(){
        remotelyt.setBackgroundColor(
            Color.parseColor(remoteConfig.getString("background")))

        if(remoteConfig.getString("image").equals("ic_sin_remote", ignoreCase = true)){
            remoteIvImage.setImageResource(R.drawable.ic_sin_remote)
        }else if(remoteConfig.getString("image").equals("ic_con_remote", ignoreCase = true)){
            remoteIvImage.setImageResource(R.drawable.ic_con_remote)
        }

        remoteTvText.text = remoteConfig.getString("text")
    }
}