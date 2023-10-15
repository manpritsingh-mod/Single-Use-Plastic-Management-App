package com.manprit.plastic_management.loginSignup

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.manprit.plastic_management.Database.Users
import com.manprit.plastic_management.R

class Reset_password : AppCompatActivity() {

    lateinit var etPassword : EditText
    lateinit var etCnfPassword: EditText
    lateinit var btnNext : Button
    lateinit var ivPwd: ImageView
    lateinit var ivPwd1: ImageView
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var rootNode: FirebaseDatabase

    lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        etPassword = findViewById(R.id.etPassword)
        etCnfPassword = findViewById(R.id.etConfirmPassword)
        btnNext = findViewById(R.id.button)
        ivPwd = findViewById(R.id.ivPwd)
        ivPwd1 = findViewById(R.id.edtConfirmPassword)
        firebaseAuth = FirebaseAuth.getInstance()

        sharedPreferences =
            getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)

        btnNext.setOnClickListener {

            if(etPassword.text.toString().length<4) {
                Toast.makeText(
                    this@Reset_password,
                    "Password can not be less than 4 character",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if(etPassword.text.toString().equals(etCnfPassword.text.toString())){

                val pass = etPassword.text.toString()
//                val name = sharedPreferences.getString("Name", null)!!
//                val email = sharedPreferences.getString("Email", null)!!
//                val phone = sharedPreferences.getString("Mobile", null)!!
//                val lname = sharedPreferences.getString("LastName", null)!!
//
//                val username = sharedPreferences.getString("UserName", null)!!

                try {
                    rootNode = FirebaseDatabase.getInstance()
                    val type = intent.getStringExtra("Type")
                    val phone = intent.getStringExtra("Phone")

                    reference = rootNode.getReference("User")

                    val userRef = reference.child(type!!).orderByChild("phone").equalTo(phone)



                    userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                for (userSnapshot in snapshot.children) {
                                    val uname = userSnapshot.child("username").value as String
                                    val name = userSnapshot.child("name").value as String
                                    val lname = userSnapshot.child("lname").value as String
                                    val phone = userSnapshot.child("phone").value as String
                                    val email = userSnapshot.child("email").value as String
                                    val uid = userSnapshot.child("uid").value as String

                                    val uRef = reference.child(type )

                                    val user = Users(
                                        name,
                                        lname,
                                        uname,
                                        email,
                                        phone,
                                        pass,
                                        uid
                                    )

                                    uRef.child(uname).setValue(user).addOnCompleteListener {
                                        val dialog = AlertDialog.Builder(this@Reset_password)
                                        dialog.setTitle("Successful !")
                                            .setMessage("Reset successful !\nPlease Login ! ")
                                            .setPositiveButton("OK") { text, listener ->
                                                sharedPreferences.edit()
                                                    .putString("Password", pass).apply()
                                                startActivity(
                                                    Intent(
                                                        this@Reset_password,
                                                        loginactivity::class.java
                                                    ).putExtra(
                                                        "type",
                                                        type
                                                    )
                                                )

                                                sharedPreferences.edit().putString("Password", pass).apply()


                                                finish()
                                            }
                                            .create()
                                            .show()
                                    }.addOnFailureListener {
                                        val dialog = AlertDialog.Builder(this@Reset_password)
                                        dialog.setTitle("Error !")
                                            .setMessage("Some error occured ! \n Try again later ")
                                            .setPositiveButton("OK") { text, listener ->
                                                finish()
                                            }
                                            .create()
                                            .show()
                                    }

                                }
                            }else{
                                val dialog = AlertDialog.Builder(this@Reset_password)
                                dialog.setTitle("Error !")
                                    .setMessage("Phone Number not found ! \n Check your details ")
                                    .setPositiveButton("OK") { text, listener ->
                                        val intent = Intent(this@Reset_password, Forget_Password::class.java).putExtra("Type",type)
                                        startActivity(intent)
                                    }
                                    .create()
                                    .show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                this@Reset_password, error.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })

//

                }catch (e : FirebaseException){
                    Toast.makeText(this,"Error ! : "+e,Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this@Reset_password,"Passwords don't match !", Toast.LENGTH_LONG).show()
            }

        }

        ivPwd.setOnClickListener {
            if (etPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                ivPwd.setImageResource(R.drawable.ic_eye_line)
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                etPassword.setSelection(etPassword.text.length)
            } else {
                ivPwd.setImageResource(R.drawable.ic_eye_off_line)
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                etPassword.setSelection(etPassword.text.length)
            }
        }

        ivPwd1.setOnClickListener {
            if (etCnfPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                ivPwd1.setImageResource(R.drawable.ic_eye_line)
                etCnfPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                etCnfPassword.setSelection(etCnfPassword.text.length)
            } else {
                ivPwd1.setImageResource(R.drawable.ic_eye_off_line)
                etCnfPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                etCnfPassword.setSelection(etCnfPassword.text.length)
            }
        }

    }
}