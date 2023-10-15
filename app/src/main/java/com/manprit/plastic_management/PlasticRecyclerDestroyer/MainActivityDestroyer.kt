package com.manprit.plastic_management.PlasticRecyclerDestroyer

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manprit.plastic_management.Fragments.DetailedHistoryFragmentDestroyer
import com.manprit.plastic_management.Fragments.NewRequestsDestroyerFragment
import com.manprit.plastic_management.Fragments.NewRequestsFragment
import com.manprit.plastic_management.Fragments.Settingsfrag
import com.manprit.plastic_management.Fragments.StatusFragment
import com.manprit.plastic_management.Fragments.contactfrag
import com.manprit.plastic_management.Fragments.detailsfrag
import com.manprit.plastic_management.Fragments.homefrag
import com.manprit.plastic_management.Fragments.homefrag2
import com.manprit.plastic_management.Fragments.homefrag2Des
import com.manprit.plastic_management.R

class MainActivityDestroyer : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth

    lateinit var bottomNavigationView: BottomNavigationView

    var previousMenuItem: MenuItem? = null

    lateinit var floatingActionBtn : FloatingActionButton

//    lateinit var btnUpdateProfile : TextView
//    lateinit var btnShowLocation : TextView
//    lateinit var btnCurrentRequest : TextView
//    lateinit var btnAboutUs : TextView
//    lateinit var btnContactUs : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_screen)

//        auth = FirebaseAuth.getInstance()


        bottomNavigationView = findViewById(R.id.bottomNavigationView)

//        btnAboutUs = findViewById(R.id.btnAboutUs)
//        btnContactUs = findViewById(R.id.btnContactUs)
//        btnCurrentRequest = findViewById(R.id.btnCurrentRequest)
//        btnShowLocation = findViewById(R.id.btnShowLocation)
//        btnUpdateProfile = findViewById(R.id.btnUpdateProfile)

        OpenHomeFrag()

        floatingActionBtn = findViewById(R.id.fab1)

        floatingActionBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                DetailedHistoryFragmentDestroyer()
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

//
//        btnAboutUs.setOnClickListener {
//
//            startActivity(Intent(this, AboutUs::class.java))
//        }
//        btnContactUs.setOnClickListener {
//
//            startActivity(Intent(this, ContactUs::class.java))
//        }
//        btnCurrentRequest.setOnClickListener {
//
//            startActivity(Intent(this, CurrentRequest::class.java))
//        }
//        btnShowLocation.setOnClickListener {
//
//            startActivity(Intent(this, ShowLocation::class.java))
//        }
//        btnUpdateProfile.setOnClickListener {
//
//            startActivity(Intent(this, UpdateUserProfile::class.java))
//        }
    }

    private fun OpenHomeFrag() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            homefrag2Des("Destroyer")
        ).commit()

        supportFragmentManager.beginTransaction().replace(
            R.id.framelayoutrequest,
            NewRequestsDestroyerFragment()
        ).commit()

        supportActionBar?.title = "DashBoard"
    }

    override fun onBackPressed() {

        val fragment = supportFragmentManager
        val frag = fragment.findFragmentById(R.id.fragment_container)

        when(frag){
            is homefrag2Des -> {
                val dialog = AlertDialog.Builder(this@MainActivityDestroyer)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes") { _, _ ->
                        finishAffinity() // If no fragments in the back stack, exit the activity
                    }
                    .setNegativeButton("No", null)
                    .create()

                dialog.show()
            }
            else ->  bottomNavigationView.selectedItemId = R.id.home
        }

    }

}