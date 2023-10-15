package com.manprit.plastic_management

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.core.os.postDelayed

class videofile : AppCompatActivity() {

    lateinit var videoView : VideoView
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video)


        videoView = findViewById(R.id.videoView1)
        button = findViewById(R.id.button4)

        val videoPath = "android.resource://" + getPackageName() + "/" + R.raw.draftwithbgm // video name here
        videoView.setVideoURI(Uri.parse(videoPath))
//        val mediaController = MediaController(this)
//        mediaController.setAnchorView(videoView)
//        videoView.setMediaController(mediaController)





        //go to next activity after completing

        videoView.setOnCompletionListener {
            // Video playback is complete, start the next activity here
            val intent = Intent(this, UserSelection::class.java)
            startActivity(intent)
            finish()
        }


        button.setOnClickListener {

            videoView.stopPlayback()
            val intent = Intent(this, UserSelection::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onPause() {

        videoView.pause()
        super.onPause()
    }

    override fun onResume(){

//        videoView.start()
        Handler().postDelayed({videoView.start()},500) // 1 sec delay
        super.onResume()
    }

}


