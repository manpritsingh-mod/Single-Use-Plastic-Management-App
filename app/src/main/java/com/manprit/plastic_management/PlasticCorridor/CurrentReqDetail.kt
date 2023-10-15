package com.manprit.plastic_management.PlasticCorridor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import com.manprit.plastic_management.Fragments.StatusFragment
import com.manprit.plastic_management.Fragments.StatusFragmentCorridor
import com.manprit.plastic_management.Fragments.StatusFragmentCorridorToRecycler
import com.manprit.plastic_management.R

class CurrentReqDetail : AppCompatActivity() {

    lateinit var cb1 : CheckBox
    lateinit var cb2 : CheckBox
    lateinit var title : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_req_detail_corridor)

        cb1 = findViewById(R.id.cb1)
        cb2 = findViewById(R.id.cb2)
        title = findViewById(R.id.DonationTitle)
        title.setText("")

        cb1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (cb1.isChecked) {
                cb2.isChecked = false
                title.setText(R.string.status_of_accepted_requests)
                supportFragmentManager.beginTransaction().replace(
                    R.id.statusFragmentHolder,
                    StatusFragmentCorridor()
                ).commit()
            }
        }

        cb2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (cb2.isChecked) {
                cb1.isChecked = false
                title.setText(R.string.request_to_recycler)
                supportFragmentManager.beginTransaction().replace(
                    R.id.statusFragmentHolder,
                    StatusFragmentCorridorToRecycler()
                ).commit()
            }
        }


    }
}