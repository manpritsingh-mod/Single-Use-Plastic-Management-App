package com.manprit.plastic_management

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manprit.plastic_management.Fragments.HistoryFragment
import com.manprit.plastic_management.Fragments.IndividualHistoryFragment

class individual_History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_history)

        supportFragmentManager.beginTransaction().replace(
            R.id.historyFragmentHolder,
            IndividualHistoryFragment()
        ).commit()
    }
}