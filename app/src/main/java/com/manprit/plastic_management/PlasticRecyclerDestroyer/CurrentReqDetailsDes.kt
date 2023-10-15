package com.manprit.plastic_management.PlasticRecyclerDestroyer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.manprit.plastic_management.Fragments.StatusFragmentDestroyer
import com.manprit.plastic_management.Fragments.StatusFragmentRecycler
import com.manprit.plastic_management.R

class CurrentReqDetailsDes : AppCompatActivity() {

    lateinit var collectionTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_req_details_des)

        val type = intent.getStringExtra("Type")

        collectionTitle = findViewById(R.id.RecyclerHeading)

        if(type.toString().equals("Recycler")){
            supportFragmentManager.beginTransaction().replace(
                R.id.statusFragmentHolder,
                StatusFragmentRecycler()
            ).commit()
        }else if (type.toString().equals("Destroyer")){

            supportFragmentManager.beginTransaction().replace(
                R.id.statusFragmentHolder,
                StatusFragmentDestroyer()
            ).commit()
            collectionTitle.text = resources.getString(R.string.request_from_recycler)
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