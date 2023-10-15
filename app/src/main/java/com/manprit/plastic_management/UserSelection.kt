package com.manprit.plastic_management

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.viewpager2.widget.ViewPager2
import com.manprit.plastic_management.Adapter.ImageAdapter
import com.manprit.plastic_management.Util.ConnectionManager
import com.manprit.plastic_management.loginSignup.loginactivity
import me.relex.circleindicator.CircleIndicator3

class UserSelection : AppCompatActivity() {


    lateinit var cbPlasticBank : Button
    lateinit var cbPlasticCorridor: Button
    lateinit var cbPlasticRecycler : Button
    lateinit var cbPlasticDestroyer : Button
    lateinit var individual : Button
    private lateinit var viewPager: ViewPager2
    private lateinit var indicator: CircleIndicator3

    lateinit var sharedPreferences : SharedPreferences

    lateinit var relLayout : RelativeLayout

    private val images = listOf(
        R.drawable.onboarding_image,
        R.drawable.onboarding_5,
        R.drawable.main_1,
        R.drawable.main_2
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_selection)

        cbPlasticBank = findViewById(R.id.PlasticBankcard)
        cbPlasticCorridor = findViewById(R.id.PlasticCorridorcard)
        cbPlasticRecycler = findViewById(R.id.PlasticRecyclercard)
        cbPlasticDestroyer = findViewById(R.id.PlasticDestroyercard)
        individual = findViewById(R.id.individual)
        relLayout = findViewById(R.id.relLayout)

        sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)

        viewPager = findViewById(R.id.viewPager)
        indicator = findViewById(R.id.indicator)

        val adapter = ImageAdapter(images)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)

        if (ConnectionManager().checkConnectivity(this)){

            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)
            val acType = sharedPreferences.getString("Type",null)
            if(isLoggedIn){
                val intent = Intent(this, loginactivity::class.java)
                intent.putExtra("type",acType)
                startActivity(intent)
                finish()
            }

            individual.setOnClickListener{
                relLayout.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(
                    R.id.frLayout,
                    plasticindividualfrag()
                ).commit()
            }

            cbPlasticBank.setOnClickListener {
                relLayout.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(
                    R.id.frLayout,
                    Plasticbankfrag()
                ).commit()
//            startActivity(Intent(this, MainActivity::class.java))
            }

            cbPlasticCorridor.setOnClickListener {
                relLayout.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(
                    R.id.frLayout,
                    plasticCorridorfrag()
                ).commit()
//            startActivity(Intent(this, plasticCorridor::class.java))
            }

            cbPlasticRecycler.setOnClickListener {
                relLayout.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(
                    R.id.frLayout,
                    plasticRecyclerfrag()
                ).commit()
                //startActivity(Intent(this, plasticRecycler::class.java))
            }

            cbPlasticDestroyer.setOnClickListener {
                relLayout.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(
                    R.id.frLayout,
                    plasticDestroyerfrag()
                ).commit()
                //startActivity(Intent(this, plasticDestroyer::class.java))
            }
        }else{
            Toast.makeText(this, "Internet not available !",Toast.LENGTH_LONG).show()
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Error !")
                .setMessage("Internet Connection not found ! ")
                .setPositiveButton("Open Settings") { text, listener ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    finish()
                }
                .setNegativeButton("Cancel") { text, listener ->
                    ActivityCompat.finishAffinity(this)
                }
                .create()
                .show()
        }

    }



    fun transaction(frag: Fragment) {

        frag.view?.visibility = View.GONE

        relLayout.visibility = View.VISIBLE
        println("=======================")
        println(frag)
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val frag = fragmentManager.findFragmentById(R.id.frLayout)

        val frag2 = frag?.view?.isGone
        if(frag2 == null || frag2 == true){
            val dialog = AlertDialog.Builder(this@UserSelection)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes") { _, _ ->
                    finishAffinity() // If no fragments in the back stack, exit the activity
                }
                .setNegativeButton("No", null)
                .create()

            dialog.show()
        }
        else{
            when (frag) {
                is plasticindividualfrag,
                is Plasticbankfrag,
                is plasticCorridorfrag,
                is plasticRecyclerfrag,
                is plasticDestroyerfrag -> {
                    transaction(frag)
                }
            }
        }


    }
}



