package com.manprit.plastic_management.OnBoardingScren

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.DrawableContainer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.manprit.plastic_management.MainActivity
import com.manprit.plastic_management.R
import com.manprit.plastic_management.UserSelection
import com.manprit.plastic_management.videofile
import java.text.FieldPosition

class OnBoardingscreen : AppCompatActivity() {

    private lateinit var onboardingIteamAdapter: OnboardingIteamAdapter
    private lateinit var indicatorsContainer: LinearLayout

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boardingscreen)

        sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)
        //val acType = sharedPreferences.getString("Type","null")
        if(isLoggedIn){
            startActivity(Intent(applicationContext,UserSelection::class.java))
            finish()
        }

        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems(){
        onboardingIteamAdapter = OnboardingIteamAdapter(
            listOf(
                OnBoardingItem(
                    onboardingImage = R.drawable.onboarding_img_1,
                    title = "WELCOME TO PLASTIC MANAGEMENT APP",
                    description = "Welcome to our application for managing single-use plastics! We are pleased to have you to our community of environmentally aware individuals working to make a good difference in the world."
                ),
                OnBoardingItem(
                    onboardingImage = R.drawable.onboarding_img_2,
                    title = "REPORT AND COLLECT PLASTIC",
                    description = "So many Plastic waste issues have negative consequences on the quality of life; Here comes our rule to help you have a good environment.. "
                ),
                OnBoardingItem(
                    onboardingImage = R.drawable.onboarding_img_3,
                    title = "LET'S GO",
                    description = "This software was specifically designed to track and eliminate single-use plastics. The application will help with single-use plastic trash management and improve public awareness of the wastes that pollute our environment.."
                )

            )

        )

        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingIteamAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.imageNext).setOnClickListener{
            if(onboardingViewPager.currentItem+1 < onboardingIteamAdapter.itemCount){
                onboardingViewPager.currentItem += 1
            }else{
                navigateToHomeActivity()
            }
        }
//        findViewById<TextView>(R.id.textSkip).setOnClickListener{
//            navigateToHomeActivity()
//        }
        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener{
            navigateToHomeActivity()
        }

    }

    private fun navigateToHomeActivity(){
        startActivity(Intent(applicationContext,videofile::class.java))
        finish()
    }

    private fun setupIndicators(){
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingIteamAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT , WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorsContainer.childCount
        for(i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            } else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }
        }
    }
}