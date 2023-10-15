package com.manprit.plastic_management.PlasticCorridor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manprit.plastic_management.Fragments.popupRequest
import com.manprit.plastic_management.R

class ReqPc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_req_pc)

        supportFragmentManager.beginTransaction().replace(
            R.id.recyclerLayoutHolder,
            popupRequest()
        ).commit()

    }
}