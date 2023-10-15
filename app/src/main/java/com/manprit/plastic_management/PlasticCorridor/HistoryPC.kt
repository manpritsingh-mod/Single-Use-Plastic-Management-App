package com.manprit.plastic_management.PlasticCorridor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manprit.plastic_management.Fragments.HistoryFragment
import com.manprit.plastic_management.Fragments.HistoryFragmentCorridor
import com.manprit.plastic_management.Fragments.StatusFragment
import com.manprit.plastic_management.Fragments.popupRequest
import com.manprit.plastic_management.R

class HistoryPC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_pc)

        supportFragmentManager.beginTransaction().replace(
            R.id.historyFragmentHolder,
            HistoryFragmentCorridor()
        ).commit()
    }
}