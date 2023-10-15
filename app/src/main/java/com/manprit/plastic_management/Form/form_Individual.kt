package com.manprit.plastic_management.Form

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.firebase.FirebaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manprit.plastic_management.Database.IndividualRequestFormData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.R
import com.manprit.plastic_management.Util.RandomString
import com.manprit.plastic_management.Util.isValidEmail
import com.manprit.plastic_management.plasticindividualfrag
import java.text.SimpleDateFormat
import java.util.Date

class form_Individual : AppCompatActivity() {

//    lateinit var cardview1: CardView
    lateinit var formTitle: TextView

    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPhone: EditText
    lateinit var etAddress: EditText


//    lateinit var cb4: CheckBox
//    lateinit var cb5: CheckBox
//    lateinit var cb6: CheckBox


    lateinit var cb7: CheckBox
    lateinit var cb8: CheckBox
    lateinit var cb9: CheckBox
    lateinit var cb10: CheckBox


    lateinit var cb12: CheckBox
    lateinit var cb13: CheckBox
    lateinit var cb14: CheckBox
    lateinit var cb15: CheckBox
    lateinit var cb16: CheckBox
    lateinit var cb17: CheckBox
    lateinit var cb18: CheckBox

    lateinit var btnSubmit: Button

    lateinit var path: String
    lateinit var status: String
    lateinit var reference: DatabaseReference
    lateinit var rootNode: FirebaseDatabase

    lateinit var sharedPreferences: SharedPreferences
    lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_individual)

//        cardview1 = findViewById(R.id.card1)
        formTitle = findViewById(R.id.textView4)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhoneNumber)
        etAddress = findViewById(R.id.etAddress)
        btnSubmit = findViewById(R.id.buttonSubmit)



//        cb4 = findViewById(R.id.checkBox4)
//        cb5 = findViewById(R.id.checkBox5)
//        cb6 = findViewById(R.id.checkBox6)

        cb7 = findViewById(R.id.checkBox7)
        cb8 = findViewById(R.id.checkBox8)
        cb9 = findViewById(R.id.checkBox9)
        cb10 = findViewById(R.id.checkBox10)


        cb12 = findViewById(R.id.checkBox12)
        cb13 = findViewById(R.id.checkBox13)
        cb14 = findViewById(R.id.checkBox14)
        cb15 = findViewById(R.id.checkBox15)
        cb16 = findViewById(R.id.checkBox16)
        cb17 = findViewById(R.id.checkBox17)
        cb18 = findViewById(R.id.checkBox18)



//        cb4.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//
//                cb5.isChecked = false
//                cb6.isChecked = false
//            }
//        }
//
//        cb5.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                cb4.isChecked = false
//                cb6.isChecked = false
//            }
//        }
//
//        cb6.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                cb4.isChecked = false
//                cb5.isChecked = false
//
//            }
//        }



        cb7.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb8.isChecked = false
                cb9.isChecked = false
                cb10.isChecked = false
            }
        }

        cb8.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb7.isChecked = false
                cb9.isChecked = false
                cb10.isChecked = false
            }
        }

        cb9.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb7.isChecked = false
                cb8.isChecked = false
                cb10.isChecked = false
            }
        }

        cb10.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb7.isChecked = false
                cb8.isChecked = false
                cb9.isChecked = false
            }
        }


        cb12.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb13.isChecked = false
                cb14.isChecked = false
                cb15.isChecked = false
                cb16.isChecked = false
                cb17.isChecked = false
                cb18.isChecked = false
            }
        }

        cb13.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb12.isChecked = false
                cb14.isChecked = false
                cb15.isChecked = false
                cb16.isChecked = false
                cb17.isChecked = false
                cb18.isChecked = false
            }
        }

        cb14.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb12.isChecked = false
                cb13.isChecked = false
                cb15.isChecked = false
                cb16.isChecked = false
                cb17.isChecked = false
                cb18.isChecked = false
            }
        }

        cb15.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb12.isChecked = false
                cb13.isChecked = false
                cb14.isChecked = false
                cb16.isChecked = false
                cb17.isChecked = false
                cb18.isChecked = false
            }
        }

        cb16.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb12.isChecked = false
                cb13.isChecked = false
                cb14.isChecked = false
                cb15.isChecked = false
                cb17.isChecked = false
                cb18.isChecked = false
            }
        }

        cb17.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb12.isChecked = false
                cb13.isChecked = false
                cb14.isChecked = false
                cb15.isChecked = false
                cb16.isChecked = false
                cb18.isChecked = false
            }
        }

        cb18.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb12.isChecked = false
                cb13.isChecked = false
                cb14.isChecked = false
                cb15.isChecked = false
                cb16.isChecked = false
                cb17.isChecked = false
            }
        }



        sharedPreferences = getSharedPreferences(
            getString(R.string.shared_pref_file),
            Context.MODE_PRIVATE
        )

        val name1 = sharedPreferences.getString("Name", "Full")
        val email1 = sharedPreferences.getString("Email", "Email Id")
        val phone1 = sharedPreferences.getString("Mobile", "Phone No")
        val lname1 = sharedPreferences.getString("LastName", "Name")
        val uid = sharedPreferences.getString("Uid", "null")

        etName.setText(name1 + " " + lname1)
        etEmail.setText(email1)
        etPhone.setText(phone1)

//        val title = intent.getStringExtra("Title")
//
//        if (title != null) {
//            formTitle.text = title
//            if (title.equals("Plastic Deposit Form")) {
//                path = "RequestFormDeposit"
//                status = "Accepted"
//            } else {
//                path = "RequestFormCollection"
//                status = "Pending"
//            }
//        }

//        formTitle.text="Plastic Deposit Form"
        path = "RequestFormDeposit"
        status = "Pending"

        btnSubmit.setOnClickListener {

            try {
                rootNode = FirebaseDatabase.getInstance()
                if (path != null) {
                    reference = rootNode.getReference(path)

                    val name = etName.text.toString()
                    val email = etEmail.text.toString()
                    val phone = etPhone.text.toString()
                    val address = etAddress.text.toString()
                    val reqID = RandomString().getAlphaNumericString(20)


//                    val donationType =
//                        if (cb4.isChecked) "School" else if (cb5.isChecked) "College" else if (cb6.isChecked) "Others" else ""
                    val plasticWeight =
                        if (cb7.isChecked) "1-2 Sacks" else if (cb8.isChecked) "4-8 Sacks" else if (cb9.isChecked) "2-4 Sacks" else if(cb10.isChecked )"8 & more" else ""

                    val ChooseBankType =
                        if (cb12.isChecked) "JPS : New Baridih" else if (cb13.isChecked) "Baridih High School : Baridih" else if (cb14.isChecked) "AIWC (Academy Of Excellence) : Dihing Rd, Baridih" else if(cb15.isChecked) "NML KPS : Bhuiyadih" else if(cb16.isChecked) "KSMS : Golmuri" else if(cb17.isChecked) "DPS : Sakchi" else if(cb18.isChecked) "SDP : Sidhgora " else ""

                    val currentDate = Date()

                    // Format date and time using SimpleDateFormat
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val timeFormat = SimpleDateFormat("HH:mm:ss")

                    val formattedDate = dateFormat.format(currentDate).toString()
                    val formattedTime = timeFormat.format(currentDate).toString()


                    if (name.equals("") || email.equals("") || phone.equals("") || address.equals("") || plasticWeight.equals("")|| ChooseBankType.equals("")) {
                        val dialog = AlertDialog.Builder(this@form_Individual)
                        dialog.setTitle("Empty Field !")
                            .setMessage("There are Field(s) missing value ! ")
                            .setPositiveButton("OK") { text, listener ->

                            }
                            .create()
                            .show()
                    }else if(!email.isValidEmail()){
                        val dialog = AlertDialog.Builder(this@form_Individual)
                        dialog.setTitle("Invalid Email Format !")
                            .setMessage("Email should be in the following format :\n\"yourmail123@gmail.com\"")
                            .setPositiveButton("OK") { text, listener ->

                            }
                            .create()
                            .show()
                    }

                    else {
                        val requestFormData = IndividualRequestFormData(
                            reqID,
                            name,
                            email,
                            phone,
                            address,
                            "donationType",
                            plasticWeight,
                            ChooseBankType,
                            formattedDate,
                            formattedTime,
                            status,
                            "Individual",
                            uid
                        )

                        reference.child(reqID!!).setValue(requestFormData).addOnCompleteListener {
                            val dialog = AlertDialog.Builder(this@form_Individual)
                            dialog.setTitle("Thank You !")
                                .setMessage("Your Request has been Successfully Submitted ! ")
                                .setPositiveButton("OK") { text, listener ->
                                    finish()
                                }
                                .create()
                                .show()
                        }.addOnFailureListener {
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
            }
            catch (e: FirebaseException) {
                Toast.makeText(this@form_Individual, "Error!! " + e, Toast.LENGTH_LONG).show()
            }

        }
    }
}
