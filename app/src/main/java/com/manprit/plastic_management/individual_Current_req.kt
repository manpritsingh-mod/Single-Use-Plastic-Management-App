package com.manprit.plastic_management

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manprit.plastic_management.Fragments.PopupRequest_individual
import com.manprit.plastic_management.Fragments.StatusFragment
import com.manprit.plastic_management.Fragments.popupRequest

class individual_Current_req : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_current_req)

        supportFragmentManager.beginTransaction().replace(
            R.id.statusFragmentHolder,
            PopupRequest_individual()
        ).commit()


    }
}