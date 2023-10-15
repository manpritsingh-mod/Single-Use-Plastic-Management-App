package com.manprit.plastic_management.loginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.manprit.plastic_management.R

class Forget_Password : AppCompatActivity() {

    lateinit var etMobile : TextView
    lateinit var etEmail : TextView
    lateinit var btnNext : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        etMobile = findViewById(R.id.etForgotMobile)
        etEmail = findViewById(R.id.etForgotEmail)
        btnNext = findViewById(R.id.btnForgotNext)

        val typeValue = intent.getStringExtra("Type")

        btnNext.setOnClickListener {
            if (etEmail.text.toString().equals("") || etMobile.text.toString().equals(""))
                Toast.makeText(this@Forget_Password, "Empty Fields !", Toast.LENGTH_LONG).show()
            else if (etMobile.text.toString().length > 10) {
                Toast.makeText(
                    this@Forget_Password,
                    "Mobile no. does not have 10 digits",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(this@Forget_Password, Reset_password::class.java)
                intent.putExtra("Phone",etMobile.text.toString())
                intent.putExtra("Type",typeValue)
                startActivity(intent)
            }
        }
    }
}