package com.manprit.plastic_management.Form

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.FirebaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.snapshots
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.R
import com.manprit.plastic_management.Util.RandomString
import com.manprit.plastic_management.Util.isValidEmail
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.properties.Delegates

class form_Corridor : AppCompatActivity() {

    lateinit var cardview1: CardView
    lateinit var formTitle: TextView

    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPhone: EditText
    lateinit var etAddress: EditText
//    lateinit var etDistrict: EditText
    lateinit var btnGetLoc : Button
    lateinit var btnSubmit: Button

    lateinit var txtLat : TextView
    lateinit var txtLong : TextView

    lateinit var cb3: CheckBox
    lateinit var cb4: CheckBox
    lateinit var cb5: CheckBox
    lateinit var cb6: CheckBox

    lateinit var cb7: CheckBox
    lateinit var cb8: CheckBox
    lateinit var cb9: CheckBox
    lateinit var cb10: CheckBox

    lateinit var reference: DatabaseReference
    lateinit var rootNode: FirebaseDatabase

    lateinit var sharedPreferences: SharedPreferences
    lateinit var database: DatabaseReference

    var longitude : String = "null"
    var latitude :String = "null"

    var removeCard by Delegates.notNull<Boolean>()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_bank_corridor)

        cardview1 = findViewById(R.id.card1)
        formTitle = findViewById(R.id.textView7)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhoneNumber)
        etAddress = findViewById(R.id.etAddress)
//        etDistrict = findViewById(R.id.etDistrict)
        btnGetLoc = findViewById(R.id.buttonGetLoc)
        btnSubmit = findViewById(R.id.buttonSubmit)

        txtLat = findViewById(R.id.txtLatitude)
        txtLong = findViewById(R.id.txtLongitude)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        cb3 = findViewById(R.id.checkBox3)
        cb4 = findViewById(R.id.checkBox4)
        cb5 = findViewById(R.id.checkBox5)
        cb6 = findViewById(R.id.checkBox6)

        cb7 = findViewById(R.id.checkBox7)
        cb8 = findViewById(R.id.checkBox8)
        cb9 = findViewById(R.id.checkBox9)
        cb10 = findViewById(R.id.checkBox10)

        cb3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb4.isChecked = false
                cb5.isChecked = false
                cb6.isChecked = false
            }
        }

        cb4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb3.isChecked = false
                cb5.isChecked = false
                cb6.isChecked = false
            }
        }

        cb5.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb4.isChecked = false
                cb3.isChecked = false
                cb6.isChecked = false
            }
        }

        cb6.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cb4.isChecked = false
                cb5.isChecked = false
                cb3.isChecked = false
            }
        }


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
        val email1 = sharedPreferences.getString("Email", "Email Id")
        val phone1 = sharedPreferences.getString("Mobile", "Phone No")
        val lname1 = sharedPreferences.getString("LastName", "Name")
        val uid = sharedPreferences.getString("Uid", "null")

        etName.setText(name1 + " " + lname1)
        etEmail.setText(email1)
        etPhone.setText(phone1)


        formTitle.text = "Request To Recycler"


        removeCard = intent.getBooleanExtra("Remove", false)

        if (removeCard)
            cardview1.visibility = View.GONE

        btnGetLoc.setOnClickListener {
            getLocation()
        }

        btnSubmit.setOnClickListener {

          try {
              rootNode = FirebaseDatabase.getInstance()

              reference = rootNode.getReference("RequestToRecycler")

              var name = etName.text.toString()
              var email = etEmail.text.toString()
              var phone = etPhone.text.toString()
              var address = etAddress.text.toString()
              val reqID = RandomString().getAlphaNumericString(20)
//            var district = etDistrict.text.toString()


              var plasticWeight =
                  if (cb7.isChecked) "1-2 Sacks " else if (cb8.isChecked) "4-8 Sacks" else if (cb9.isChecked) "2-4 Sacks" else if(cb10.isChecked) "8 & more" else ""

              val currentDate = Date()

              // Format date and time using SimpleDateFormat
              val dateFormat = SimpleDateFormat("dd-MM-yyyy")
              val timeFormat = SimpleDateFormat("HH:mm:ss")

              val formattedDate = dateFormat.format(currentDate).toString()
              val formattedTime = timeFormat.format(currentDate).toString()


              if (name.equals("") || email.equals("") || phone.equals("") || address.equals("") || plasticWeight.equals("")) {
                  val dialog = AlertDialog.Builder(this@form_Corridor)
                  dialog.setTitle("Empty Field !")
                      .setMessage("There are Field(s) missing value ! ")
                      .setPositiveButton("OK") { text, listener ->

                      }
                      .create()
                      .show()
              }else if(!email.isValidEmail()){
                  val dialog = AlertDialog.Builder(this@form_Corridor)
                  dialog.setTitle("Invalid Email Format !")
                      .setMessage("Email should be in the following format :\n\"yourmail123@gmail.com\"")
                      .setPositiveButton("OK") { text, listener ->

                      }
                      .create()
                      .show()
              }else if(latitude.equals("null")||longitude.equals("null")){

                  val dialog = AlertDialog.Builder(this@form_Corridor)
                  dialog.setTitle("Location Error !")
                      .setMessage("You have not selected location !\nProceed without selecting ?")
                      .setPositiveButton("OK") { text, listener ->

                          val requestFormData = RequestFormData(
                              reqID,
                              name,
                              email,
                              phone,
                              address,
//                district,
                              null,
                              plasticWeight,
                              formattedDate,
                              formattedTime,
                              "Pending",
                              "N/A",
                              "N/A",
                              "Corridor",
                              uid
                          )



                          reference.child(reqID!!).setValue(requestFormData).addOnCompleteListener {
                              val newdialog = AlertDialog.Builder(this@form_Corridor)
                              newdialog.setTitle("Thank You !")
                                  .setMessage("Your Request has been Successfully Submitted ! ")
                                  .setPositiveButton("OK") { text, listener ->
                                      finish()
                                  }
                                  .create()
                                  .show()
                          }.addOnFailureListener {
                              val newdialog = AlertDialog.Builder(this@form_Corridor)
                              newdialog.setTitle("Error !")
                                  .setMessage("Some error occured ! \n Try again later ")
                                  .setPositiveButton("OK") { text, listener ->
                                      finish()
                                  }
                                  .create()
                                  .show()
                          }


                      }
                      .setNegativeButton("Cancel"){_,_->

                      }
                      .create()
                      .show()
              }

              else {
                  val requestFormData = RequestFormData(
                      reqID,
                      name,
                      email,
                      phone,
                      address,
//                district,
                      null,
                      plasticWeight,
                      formattedDate,
                      formattedTime,
                      "Pending",
                      latitude,
                      longitude,
                      "Corridor",
                      uid
                  )



                  reference.child(reqID!!).setValue(requestFormData).addOnCompleteListener {
                      val dialog = AlertDialog.Builder(this@form_Corridor)
                      dialog.setTitle("Thank You !")
                          .setMessage("Your Request has been Successfully Submitted ! ")
                          .setPositiveButton("OK") { text, listener ->
                              finish()
                          }
                          .create()
                          .show()
                  }.addOnFailureListener {
                      val dialog = AlertDialog.Builder(this@form_Corridor)
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
              Toast.makeText(this@form_Corridor, "Error!! " + e, Toast.LENGTH_LONG).show()
          }

        }
    }

    private fun getLocation() {

        val permissioncode = 100
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),
                permissioncode
            )
            return
        }

        val location = fusedLocationProviderClient.lastLocation.addOnSuccessListener {

            if (it!=  null){
                val latitude = "Latitude : "+it.latitude.toString()
                val longitude = "Longitude : "+it.longitude.toString()
                txtLat.text = latitude
                txtLong.text = longitude

                this.latitude = it.latitude.toString()
                this.longitude = it.longitude.toString()

            }
        }.addOnFailureListener{
            Toast.makeText(this,"Map Error! ", Toast.LENGTH_LONG).show()
        }
    }

}
