package com.manprit.plastic_management.PlasticBank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manprit.plastic_management.Fragments.HistoryFragment
import com.manprit.plastic_management.Fragments.StatusFragment
import com.manprit.plastic_management.R
import com.manprit.plastic_management.Fragments.popupRequest

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportFragmentManager.beginTransaction().replace(
            R.id.historyFragmentHolder,
            HistoryFragment()
        ).commit()
    }
}