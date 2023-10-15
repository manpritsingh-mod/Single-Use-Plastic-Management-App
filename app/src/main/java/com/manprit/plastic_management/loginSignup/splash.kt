package com.manprit.plastic_management.loginSignup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.manprit.plastic_management.OnBoardingScren.OnBoardingscreen
import com.manprit.plastic_management.R


class splash : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        Handler().postDelayed({
            startActivity(Intent(this@splash, OnBoardingscreen::class.java))
            finish()
        }, 2000)
    }

}