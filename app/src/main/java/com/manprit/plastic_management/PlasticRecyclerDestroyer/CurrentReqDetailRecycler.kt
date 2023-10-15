package com.manprit.plastic_management.PlasticRecyclerDestroyer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import com.manprit.plastic_management.Fragments.MyReqFragmentRecycler
import com.manprit.plastic_management.Fragments.MyReqFragmentRecyclerToDestroyer
import com.manprit.plastic_management.Fragments.StatusFragment
import com.manprit.plastic_management.Fragments.StatusFragmentCorridor
import com.manprit.plastic_management.Fragments.StatusFragmentCorridorToRecycler
import com.manprit.plastic_management.R


class CurrentReqDetailRecycler : AppCompatActivity() {

    lateinit var DonationTitle: TextView
    lateinit var cb1 : CheckBox
    lateinit var cb2 : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_req_detail)


        DonationTitle = findViewById(R.id.DonationTitle)
        cb1 = findViewById(R.id.cb1)
        cb2 = findViewById(R.id.cb2)
        DonationTitle.setText("")

        cb1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (cb1.isChecked) {
                cb2.isChecked = false
                DonationTitle.setText(R.string.request_from_corridor)

                supportFragmentManager.beginTransaction().replace(
                    R.id.statusFragmentHolder,
                    MyReqFragmentRecycler()
                ).commit()
            }
        }

        cb2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (cb2.isChecked) {
                cb1.isChecked = false
                DonationTitle.setText(R.string.request_to_destroyer)

                supportFragmentManager.beginTransaction().replace(
                    R.id.statusFragmentHolder,
                    MyReqFragmentRecyclerToDestroyer()
                ).commit()
            }
            }
        }


}