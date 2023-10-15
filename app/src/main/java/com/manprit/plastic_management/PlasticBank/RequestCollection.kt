package com.manprit.plastic_management.PlasticBank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manprit.plastic_management.Fragments.DonationFragment
import com.manprit.plastic_management.Fragments.StatusFragment
import com.manprit.plastic_management.R

class RequestCollection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        supportFragmentManager.beginTransaction().replace(
            R.id.statusFragmentHolder,
            DonationFragment()
        ).commit()
    }


}