package com.manprit.plastic_management.PlasticBank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manprit.plastic_management.Fragments.StatusFragment
import com.manprit.plastic_management.R
import com.manprit.plastic_management.Fragments.popupRequest

class CurrentStatus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_status)

        supportFragmentManager.beginTransaction().replace(
            R.id.statusFragmentHolder,
            StatusFragment()
        ).commit()
    }
}