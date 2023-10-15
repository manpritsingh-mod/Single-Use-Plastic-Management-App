package com.manprit.plastic_management

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.manprit.plastic_management.PlasticBank.CurrentRequest
import com.manprit.plastic_management.PlasticBank.CurrentStatus
import com.manprit.plastic_management.PlasticBank.History
import com.manprit.plastic_management.PlasticBank.RequestCollection
import com.manprit.plastic_management.PlasticBank.UpdateUserProfile
import com.manprit.plastic_management.loginSignup.loginactivity

class plasticCorridor : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var logoutBtn: ImageView
    private lateinit var requestBtn: ImageView
    private lateinit var statusBtn: ImageView
    private lateinit var historyBtn: ImageView
    lateinit var btnUpdateProfile : TextView
    lateinit var btnShowLocation : TextView
    lateinit var btnCurrentRequest : TextView
    lateinit var btnAboutUs : TextView
    lateinit var btnContactUs : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plastic_corridor)

        auth = FirebaseAuth.getInstance()
        logoutBtn = findViewById(R.id.btnLogout)
        requestBtn = findViewById(R.id.btnRequest)
        statusBtn = findViewById(R.id.btnCurrentStatus)
        historyBtn = findViewById(R.id.btnHistory)
        btnAboutUs = findViewById(R.id.btnAboutUs)
        btnContactUs = findViewById(R.id.btnContactUs)
        btnCurrentRequest = findViewById(R.id.btnCurrentRequest)
        btnShowLocation = findViewById(R.id.btnShowLocation)
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile)

        logoutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, loginactivity::class.java))
        }

        requestBtn.setOnClickListener {

            startActivity(Intent(this, RequestCollection::class.java))
        }
        statusBtn.setOnClickListener {

            startActivity(Intent(this, CurrentStatus::class.java))
        }
        historyBtn.setOnClickListener {

            startActivity(Intent(this, History::class.java))
        }

        btnAboutUs.setOnClickListener {

            startActivity(Intent(this, AboutUs::class.java))
        }
        btnContactUs.setOnClickListener {

            startActivity(Intent(this, ContactUs::class.java))
        }
        btnCurrentRequest.setOnClickListener {

            startActivity(Intent(this, CurrentRequest::class.java))
        }
        btnShowLocation.setOnClickListener {

            startActivity(Intent(this, ShowLocation::class.java))
        }
        btnUpdateProfile.setOnClickListener {

            startActivity(Intent(this, UpdateUserProfile::class.java))
        }
    }
}