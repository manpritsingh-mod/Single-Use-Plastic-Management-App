package com.manprit.plastic_management.Form

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.android.gms.location.LocationServices
import com.google.firebase.FirebaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manprit.plastic_management.Database.RecyclerFormData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.R
import com.manprit.plastic_management.Util.RandomString
import com.manprit.plastic_management.Util.isValidEmail
import java.text.SimpleDateFormat
import java.util.Date

class form_Recycler_Destroyer : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etPhone: EditText
    lateinit var btnSubmit: Button
    lateinit var etAddress : EditText

//    lateinit var cb3: CheckBox
    lateinit var cb4: CheckBox
//    lateinit var cb5: CheckBox
    lateinit var cb6: CheckBox
    lateinit var cb7: CheckBox
    lateinit var cb8: CheckBox
    lateinit var cb9: CheckBox
    lateinit var cb10: CheckBox

    lateinit var reference: DatabaseReference
    lateinit var rootNode: FirebaseDatabase

    lateinit var sharedPreferences: SharedPreferences
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_recycler_destroyer)


        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhoneNumber)
        btnSubmit = findViewById(R.id.buttonSubmit)
        etAddress = findViewById(R.id.etAddress)

//        cb3 = findViewById(R.id.checkBox3)
//        cb5 = findViewById(R.id.checkBox5)
        cb7 = findViewById(R.id.checkBox7)
        cb8 = findViewById(R.id.checkBox8)
        cb9 = findViewById(R.id.checkBox9)
        cb10 = findViewById(R.id.checkBox10)

//        cb3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                cb5.isChecked = false
//            }
//        }
//

//        cb5.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                cb4.isChecked = false
//                cb3.isChecked = false
//                cb6.isChecked = false
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


        sharedPreferences = getSharedPreferences(
            getString(R.string.shared_pref_file),
            Context.MODE_PRIVATE
        )

        val name1 = sharedPreferences.getString("Name", "Full")
        val phone1 = sharedPreferences.getString("Mobile", "Phone No")
        val lname1 = sharedPreferences.getString("LastName", "Name")
        val uid = sharedPreferences.getString("Uid", "null")
        etName.setText(name1 + " " + lname1)
        etPhone.setText(phone1)


        btnSubmit.setOnClickListener {

            try {
                rootNode = FirebaseDatabase.getInstance()

                reference = rootNode.getReference("RequestRecyclerToDestroyer")

                var name = etName.text.toString()
                var phone = etPhone.text.toString()
                var Address = etAddress.text.toString()
                var Status = "Pending"
                val reqID = RandomString().getAlphaNumericString(20)

//            var donationType = if (cb3.isChecked) "Granules" else "Simple Plastic"

                var plasticWeight =
                    if (cb7.isChecked) "1-10" else if (cb8.isChecked) "30-50" else if (cb9.isChecked) "10-30" else if(cb10.isChecked)  "50-100" else ""

                val currentDate = Date()

                // Format date and time using SimpleDateFormat
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val timeFormat = SimpleDateFormat("HH:mm:ss")

                val formattedDate = dateFormat.format(currentDate).toString()
                val formattedTime = timeFormat.format(currentDate).toString()

                if (name.equals("") || phone.equals("") || Address.equals("") || plasticWeight.equals("")) {
                    val dialog = AlertDialog.Builder(this@form_Recycler_Destroyer)
                    dialog.setTitle("Empty Field !")
                        .setMessage("There are Field(s) missing value ! ")
                        .setPositiveButton("OK") { text, listener ->

                        }
                        .create()
                        .show()
                }
                else {

                    val requestFormData = RecyclerFormData(
                        reqID,
                        name,
                        phone,
                        "Granules",
                        Address,
                        plasticWeight,
                        formattedDate,
                        formattedTime,
                        Status,
                        "Recycler",
                        uid
                    )

                    reference.child(reqID!!).setValue(requestFormData).addOnCompleteListener {
                        val dialog = AlertDialog.Builder(this)
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
            catch (e: FirebaseException) {
                Toast.makeText(this@form_Recycler_Destroyer, "Error!! " + e, Toast.LENGTH_LONG).show()
            }
            }
        }
}