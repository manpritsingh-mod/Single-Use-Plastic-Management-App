package com.manprit.plastic_management.PlasticBank

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manprit.plastic_management.Fragments.DetailedHistoryFragment
import com.manprit.plastic_management.Fragments.PopupRequest_individual
import com.manprit.plastic_management.Fragments.Settingsfrag
import com.manprit.plastic_management.Fragments.contactfrag
import com.manprit.plastic_management.Fragments.detailsfrag
import com.manprit.plastic_management.Fragments.homefrag
import com.manprit.plastic_management.R

class MainActivityBank : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth

    lateinit var bottomNavigationView: BottomNavigationView

    lateinit var typeValue : String

    var previousMenuItem: MenuItem? = null

    lateinit var floatingActionBtn : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_screen)



//        auth = FirebaseAuth.getInstance()

        typeValue  = intent.getStringExtra("Corridor") ?: "No"


        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        OpenHomeFrag()

        floatingActionBtn = findViewById(R.id.fab1)

        floatingActionBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                DetailedHistoryFragment()
            ).commit()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (previousMenuItem != null)
                previousMenuItem?.isChecked = false

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.home -> {
                    OpenHomeFrag()
                }
                R.id.details -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        detailsfrag()
                    ).commit()

                    supportActionBar?.title = "Details"
                }
//                R.id.blank -> {
//                }
                R.id.contactUs -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        contactfrag()
                    ).commit()

                    supportActionBar?.title = "Contact Us"
                }
                R.id.settings -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        Settingsfrag()
                    ).commit()
                    supportActionBar?.title = "Settings"
                }
            }

            return@setOnNavigationItemSelectedListener true
        }

    }

    private fun OpenHomeFrag() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            typeValue!!.let { homefrag(it) }
        ).commit()

        supportFragmentManager.beginTransaction().replace(
            R.id.recyclerLayoutHolder,
            PopupRequest_individual()
        ).commit()

        supportActionBar?.title = "DashBoard"
    }

    override fun onBackPressed() {

        val fragment = supportFragmentManager
        val frag = fragment.findFragmentById(R.id.fragment_container)

        when(frag){
            is homefrag -> {
                val dialog = AlertDialog.Builder(this@MainActivityBank)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes") { _, _ ->
                        finishAffinity() // If no fragments in the back stack, exit the activity
                    }
                    .setNegativeButton("No", null)
                    .create()

                dialog.show()
            }
            else -> bottomNavigationView.selectedItemId = R.id.home
        }

    }
}