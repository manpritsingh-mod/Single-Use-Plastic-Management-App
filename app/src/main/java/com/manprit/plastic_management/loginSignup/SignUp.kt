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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Database.Users
import com.manprit.plastic_management.R
import com.manprit.plastic_management.Util.isValidEmail

class SignUp : AppCompatActivity() {

    lateinit var etPassword: EditText
    lateinit var etMobileNumber: EditText
    lateinit var btnRegister: Button
    lateinit var txtLogin: TextView
    lateinit var edtConfirmPassword: EditText
    lateinit var etUsername: EditText
    lateinit var etName: EditText
    lateinit var etLastName: EditText
    lateinit var etEmail: EditText
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var ivPwd: ImageView
    lateinit var ivPwd1: ImageView

    lateinit var reference: DatabaseReference
    lateinit var rootNode: FirebaseDatabase

    lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etName = findViewById(R.id.etName)
        etLastName = findViewById(R.id.etLastName)
        etUsername = findViewById(R.id.edtUsername)
        etEmail = findViewById(R.id.etEmail)
        etMobileNumber = findViewById(R.id.etPhoneNumber)
        etPassword = findViewById(R.id.etPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)
        txtLogin = findViewById(R.id.btnSignIn)
        btnRegister = findViewById(R.id.button)
        ivPwd = findViewById(R.id.ivPwd)
        ivPwd1 = findViewById(R.id.etConfirmPassword)

        firebaseAuth = FirebaseAuth.getInstance()


        btnRegister.setOnClickListener {
//            val intent = Intent(this@SignUp, loginactivity::class.java)

            var name = etName.text.toString()
            var lName = etLastName.text.toString()
            var pass = etPassword.text.toString()
            var cnfPass = edtConfirmPassword.text.toString()
            var email = etEmail.text.toString()
            var phone = etMobileNumber.text.toString()
            var username = etUsername.text.toString()

            if (name.equals("") || lName.equals("") || username.equals("") || pass.equals("") || cnfPass.equals(
                    ""
                ) || email.equals("") || phone.equals("")
            ) {
                Toast.makeText(this@SignUp, "Empty Fields !", Toast.LENGTH_LONG).show()
            }else if(!email.isValidEmail()){
                val dialog = android.app.AlertDialog.Builder(this@SignUp)
                dialog.setTitle("Invalid Email Format !")
                    .setMessage("Email should be in the following format :\n\"yourmail123@gmail.com\"")
                    .setPositiveButton("OK") { text, listener ->

                    }
                    .create()
                    .show()
            }
            else {
                if (pass.equals(cnfPass)) {

                    createUser(name, lName, username, email, phone, pass)

//                    firebaseAuth.createUserWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString()).addOnCompleteListener {
//
//                        if(it.isSuccessful)
//                        {
//                            startActivity(intent)
//                            finish()
//                        }else{
//                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
//                        }
//                    }

                } else {
                    Toast.makeText(this@SignUp, "Passwords don't match !", Toast.LENGTH_LONG).show()
                }
            }
        }
        txtLogin.setOnClickListener {
            val intent = Intent(this@SignUp, loginactivity::class.java)
            startActivity(intent)
            finish()
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
            if (edtConfirmPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                ivPwd1.setImageResource(R.drawable.ic_eye_line)
                edtConfirmPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                edtConfirmPassword.setSelection(edtConfirmPassword.text.length)
            } else {
                ivPwd1.setImageResource(R.drawable.ic_eye_off_line)
                edtConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                edtConfirmPassword.setSelection(edtConfirmPassword.text.length)
            }
        }

    }


    private fun createUser(
        name: String,
        lname: String,
        username: String,
        email: String,
        phone: String,
        pass: String
    ) {

        try {
            rootNode = FirebaseDatabase.getInstance()

            reference = rootNode.getReference("User")

            sharedPreferences =
                getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)

            val type = intent.getStringExtra("Type")!!

            val userRef = reference.child(type + "/" + username)

            val uRef = reference.child(type)
            val chkMail = uRef.orderByChild("email").equalTo(email)
            val chkPhone = uRef.orderByChild("phone").equalTo(phone)

            var userCount = 0

            uRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userCount = snapshot.childrenCount.toInt()
                    println("--------------")
                    println(snapshot)
                    println(userCount)
                    println(userRef)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@SignUp, error.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            chkMail.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        val dialog = AlertDialog.Builder(this@SignUp)
                        dialog.setTitle("Error !")
                            .setMessage("Email already exists !")
                            .setPositiveButton("OK") { text, listener ->

                            }
                            .create()
                            .show()
                    } else {
                        chkPhone.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    val dialog = AlertDialog.Builder(this@SignUp)
                                    dialog.setTitle("Error !")
                                        .setMessage("Mobile no. already exists !")
                                        .setPositiveButton("OK") { text, listener ->

                                        }
                                        .create()
                                        .show()
                                } else {
                                    userRef.addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            if (!snapshot.exists()) {

                                                var uid = "null"

                                                val user =
                                                    Users(name, lname, username, email, phone, pass,uid)

                                                userCount++

                                                var newUniqueId = "null"
                                                if(type.equals("Individual")){
                                                newUniqueId = "IND${String.format("%04d", userCount)}"
                                                user.uid = newUniqueId
                                                }
                                                else if(type.equals("Bank")){
                                                    newUniqueId = "PB${String.format("%04d", userCount)}"
                                                    user.uid = newUniqueId
                                                }
                                                else if(type.equals("Corridor")){
                                                    newUniqueId = "PC${String.format("%04d", userCount)}"
                                                    user.uid = newUniqueId
                                                }
                                                else if(type.equals("Recycler")){
                                                    newUniqueId = "PR${String.format("%04d", userCount)}"
                                                    user.uid = newUniqueId
                                                }
                                                else if(type.equals("Destroyer")){
                                                    newUniqueId = "PD${String.format("%04d", userCount)}"
                                                    user.uid = newUniqueId
                                                }

                                                userRef.setValue(user).addOnCompleteListener {
                                                    val dialog = AlertDialog.Builder(this@SignUp)
                                                    dialog.setTitle("Successful !")
                                                        .setMessage("Sign Up Successfull !\nPlease Login ! ")
                                                        .setPositiveButton("OK") { text, listener ->
                                                            startActivity(
                                                                Intent(
                                                                    this@SignUp,
                                                                    loginactivity::class.java
                                                                ).putExtra(
                                                                    "type",
                                                                    type
                                                                )
                                                            )

                                                            sharedPreferences.edit()
                                                                .putString("Name", name).apply()
                                                            sharedPreferences.edit()
                                                                .putString("LastName", lname)
                                                                .apply()
                                                            sharedPreferences.edit()
                                                                .putString("UserName", username)
                                                                .apply()
                                                            sharedPreferences.edit()
                                                                .putString("Email", email).apply()
                                                            sharedPreferences.edit()
                                                                .putString("Mobile", phone).apply()
                                                            sharedPreferences.edit()
                                                                .putString("Password", pass).apply()
                                                            sharedPreferences.edit()
                                                                .putString("Type", type).apply()
                                                            sharedPreferences.edit()
                                                                .putString("Uid", newUniqueId).apply()

                                                            finishAffinity()
                                                        }
                                                        .create()
                                                        .show()
                                                }.addOnFailureListener {
                                                    val dialog = AlertDialog.Builder(this@SignUp)
                                                    dialog.setTitle("Error !")
                                                        .setMessage("Some error occured ! \n Try again later ")
                                                        .setPositiveButton("OK") { text, listener ->
                                                            finish()
                                                        }
                                                        .create()
                                                        .show()
                                                }
                                            } else {
                                                val dialog = AlertDialog.Builder(this@SignUp)
                                                dialog.setTitle("Error !")
                                                    .setMessage("Username already exists !")
                                                    .setPositiveButton("OK") { text, listener ->
                                                        etUsername.setText("")
                                                    }
                                                    .create()
                                                    .show()
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            Toast.makeText(
                                                this@SignUp, error.toString(),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }


                                    })
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(
                                    this@SignUp, error.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@SignUp, error.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
        catch (e: FirebaseException) {
            Toast.makeText(this@SignUp, "Error!! " + e, Toast.LENGTH_LONG).show()
        }


    }
}