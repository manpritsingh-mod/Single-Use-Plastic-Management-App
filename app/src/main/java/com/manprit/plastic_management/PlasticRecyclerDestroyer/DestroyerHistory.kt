package com.manprit.plastic_management.PlasticRecyclerDestroyer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.manprit.plastic_management.Fragments.HistoryFragmentDestroyer
import com.manprit.plastic_management.Fragments.HistoryFragmentRecycler
import com.manprit.plastic_management.Fragments.NewRequestsFragment
import com.manprit.plastic_management.Fragments.StatusFragmentDestroyer
import com.manprit.plastic_management.Fragments.StatusFragmentRecycler
import com.manprit.plastic_management.R

class DestroyerHistory : AppCompatActivity() {

    lateinit var historyTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destroyer_history)

        val type = intent.getStringExtra("Type")
        historyTitle = findViewById(R.id.RecyclerHistory)

        if(type.toString().equals("Recycler")){
            supportFragmentManager.beginTransaction().replace(
                R.id.statusFragmentHolder,
                HistoryFragmentRecycler()
            ).commit()
        }else if (type.toString().equals("Destroyer")){
            supportFragmentManager.beginTransaction().replace(
                R.id.statusFragmentHolder,
                HistoryFragmentDestroyer()
            ).commit()

            historyTitle.text = resources.getString(R.string.u_history_destoroyer)

        }else{
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Error !")
                .setMessage("Some error occured ! \n Try again later ")
                .setPositiveButton("OK") { text, listener ->
                    finish()
                }
                .create()
                .show()
        }
    }
}