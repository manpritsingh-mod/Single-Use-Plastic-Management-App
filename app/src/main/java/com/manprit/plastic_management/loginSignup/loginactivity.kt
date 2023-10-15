package com.manprit.plastic_management.loginSignup

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.manprit.plastic_management.PlasticBank.MainActivityBank
import com.manprit.plastic_management.PlasticCorridor.MainActivityCorridor
import com.manprit.plastic_management.PlasticRecyclerDestroyer.MainActivityRecyclerDestroyer
import com.manprit.plastic_management.R
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.manprit.plastic_management.MainActivityIndividual
import com.manprit.plastic_management.PlasticRecyclerDestroyer.MainActivityDestroyer
import com.manprit.plastic_management.Util.isValidEmail

class loginactivity : AppCompatActivity() {

    lateinit var etPassword: EditText
    lateinit var editEmail: AutoCompleteTextView
    lateinit var btnLogin: Button
    lateinit var txtSignUp: Button
    lateinit var txtForgotPassword: TextView
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var ivPwd :ImageView
    lateinit var cbRemember : CheckBox
    lateinit var progressbar: ConstraintLayout
    lateinit var loginType: TextView

    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var reference: DatabaseReference
    lateinit var rootNode: FirebaseDatabase

    lateinit var sharedPreferences : SharedPreferences

//    val RC_SIGN_IN =123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)

        val typeValue = intent.getStringExtra("type")

        sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)


        progressbar = findViewById(R.id.progressbar)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)
        //val acType = sharedPreferences.getString("Type","null")
        if(isLoggedIn){
            val email = sharedPreferences.getString("Email",null)
            val pass = sharedPreferences.getString("Password",null)
           Login(email,pass)
        }
        else{
            progressbar.visibility = View.GONE
        }


        firebaseAuth = FirebaseAuth.getInstance()
        btnLogin = findViewById(R.id.button)
        txtSignUp = findViewById(R.id.txtRegisterYourself)
        etPassword = findViewById(R.id.editTextTextPassword)
        editEmail = findViewById(R.id.etEmail)
        cbRemember = findViewById(R.id.checkBox)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        ivPwd = findViewById(R.id.ivPwd)
        loginType = findViewById(R.id.loginType)

        if(typeValue.equals("Bank")){
            loginType.text = "Plastic Bank"
        }
        else if(typeValue.equals("Corridor")){
            loginType.text = "Plastic Corridor"

        }
        else if(typeValue.equals("Recycler")) {
            loginType.text = "Plastic Recycler"

        }
        else if(typeValue.equals("Destroyer")) {
            loginType.text = "Plastic Destroyer"

        }


//        etPassword.setAutofillHints(View.AUTOFILL_HINT_PASSWORD)
//        val continueWithGoogleButton = findViewById<ImageView>(R.id.google_icon)
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//
//        continueWithGoogleButton.setOnClickListener {
//            signInWithGoogle()
//        }

//        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val email = sharedPreferences.getString("Email",null)
        val suggestions = arrayOf(email)
        if (!suggestions.isNullOrEmpty()){
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestions)
        editEmail.setAdapter(adapter)
        }

        btnLogin.setOnClickListener {

            if(etPassword.text.toString().equals("") || editEmail.text.toString().equals(""))
            {
                Toast.makeText(this@loginactivity,"Empty Fields !", Toast.LENGTH_LONG).show()
            }

            else {

                val email = editEmail.text.toString()
                val pass = etPassword.text.toString()

                if(email.equals("") || pass.equals(""))
                {
                    Toast.makeText(this@loginactivity,"Empty Fields !", Toast.LENGTH_LONG).show()
                }
                else if(!email.isValidEmail()){
                    println("-----------------------------------")
                    val dialog = AlertDialog.Builder(this@loginactivity)
                    dialog.setTitle("Invalid Email Format !")
                        .setMessage("Email should be in the following format :\n\"yourmail123@gmail.com\"")
                        .setPositiveButton("OK") { text, listener ->

                        }
                        .create()
                        .show()
                }
                else {

                    Login(email,pass)
                }
            }
        }

        txtSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java).putExtra("Type",typeValue)
            startActivity(intent)
        }

        txtForgotPassword.setOnClickListener {
            val intent = Intent(this@loginactivity, Forget_Password::class.java).putExtra("Type",typeValue)
            startActivity(intent)
        }


        ivPwd.setOnClickListener {
            if (etPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                ivPwd.setImageResource(R.drawable.ic_eye_off_line)
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                etPassword.setSelection(etPassword.text.length)
            } else {
                ivPwd.setImageResource(R.drawable.ic_eye_line)
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                etPassword.setSelection(etPassword.text.length)
            }
        }


    }

    private fun Login(email: String?, pass: String?) {

        if(email == null || pass == null){
            val dialog = AlertDialog.Builder(this@loginactivity)
            dialog.setTitle("Error !")
                .setMessage("Error Logging in !")
                .setPositiveButton("Try again") { text, listener ->

                    finishAffinity()
                }
                .create()
                .show()
        }else {

        val typeValue = intent.getStringExtra("type")
        try {

                rootNode = FirebaseDatabase.getInstance()

                reference = rootNode.getReference("User")

                val userRef = reference.child(typeValue!!).orderByChild("email").equalTo(email)

                userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {

                                val pwd = userSnapshot.child("password").value as String

                                if (pwd.equals(pass)) {

                                    val uname = userSnapshot.child("username").value as String
                                    val name = userSnapshot.child("name").value as String
                                    val lname = userSnapshot.child("lname").value as String
                                    val phone = userSnapshot.child("phone").value as String
                                    val uid = userSnapshot.child("uid").value as String

                                    if (cbRemember.isChecked) {
                                        sharedPreferences.edit().putBoolean("isLoggedIn", true)
                                            .apply()

                                    }

                                    sharedPreferences.edit().putString("Type", typeValue).apply()
                                    sharedPreferences.edit().putString("Email", email).apply()
                                    sharedPreferences.edit().putString("Password", pass).apply()
                                    sharedPreferences.edit().putString("UserName", uname).apply()
                                    sharedPreferences.edit().putString("Name", name).apply()
                                    sharedPreferences.edit().putString("LastName", lname).apply()
                                    sharedPreferences.edit().putString("Mobile", phone).apply()
                                    sharedPreferences.edit().putString("Uid", uid).apply()


                                    when (typeValue) {

                                        "Individual" -> {
                                            val intent2 =
                                                Intent(
                                                    this@loginactivity,
                                                    MainActivityIndividual::class.java
                                                )
                                            intent2.putExtra("Individual", "Yes")
                                            startActivity(intent2)
                                            finish()
                                        }

                                        "Bank" -> {
                                            val intent2 =
                                                Intent(
                                                    this@loginactivity,
                                                    MainActivityBank::class.java
                                                )
                                            intent2.putExtra("Corridor", "No")
                                            startActivity(intent2)
                                            finish()
                                        }

                                        "Corridor" -> {
                                            val intent2 =
                                                Intent(
                                                    this@loginactivity,
                                                    MainActivityCorridor::class.java
                                                )
                                            intent2.putExtra("Corridor", "Yes")
                                            startActivity(intent2)
                                            finish()
                                        }

                                        "Recycler" -> {
                                            val intent2 = Intent(
                                                this@loginactivity,
                                                MainActivityRecyclerDestroyer::class.java
                                            )
                                            intent2.putExtra("Destroyer", "No")
                                            startActivity(intent2)
                                            finish()
                                        }

                                        else -> {
                                            val intent2 = Intent(
                                                this@loginactivity,
                                                MainActivityDestroyer::class.java
                                            )
                                            intent2.putExtra("Destroyer", "Yes")
                                            startActivity(intent2)
                                            finish()
                                        }
                                    }


                                } else {

                                    val dialog = AlertDialog.Builder(this@loginactivity)
                                    dialog.setTitle("Error !")
                                        .setMessage("Incorrect password !")
                                        .setPositiveButton("OK") { text, listener ->

                                        }
                                        .create()
                                        .show()
                                }
                            }
                        } else {
                            val dialog = AlertDialog.Builder(this@loginactivity)
                            dialog.setTitle("Error !")
                                .setMessage("Email not registered !!\nPlease Sign Up first !")
                                .setPositiveButton("SignUp") { text, listener ->
                                    startActivity(
                                        Intent(
                                            this@loginactivity,
                                            SignUp::class.java
                                        ).putExtra("Type", typeValue)
                                    )
                                }
                                .setNegativeButton("Cancel") { text, listener ->

                                }
                                .create()
                                .show()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@loginactivity, error.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            }
            catch (e: FirebaseException) {
                Toast.makeText(this@loginactivity, "Error!! " + e, Toast.LENGTH_LONG).show()
            }


//                firebaseAuth.signInWithEmailAndPassword(editEmail.text.toString(),etPassword.text.toString()).addOnCompleteListener {
//
//                    if(it.isSuccessful)
//                    {
//                        when (typeValue) {
//
//                            "Bank" -> {
//                                val intent2 = Intent(this@loginactivity, MainActivityBank ::class.java)
//                                intent2.putExtra("Corridor","No")
//                                startActivity(intent2)
//                                finish()
//                            }
//                            "Corridor" -> {
//                                val intent2 = Intent(this@loginactivity, MainActivityCorridor ::class.java)
//                                intent2.putExtra("Corridor","Yes")
//                                startActivity(intent2)
//                                finish()
//                            }
//                            "Recycler" -> {
//                                val intent2 = Intent(this@loginactivity, MainActivityRecyclerDestroyer ::class.java)
//                                intent2.putExtra("Destroyer","No")
//                                startActivity(intent2)
//                                finish()
//                            }
//                            else -> {
//                                val intent2 = Intent(this@loginactivity, MainActivityRecyclerDestroyer ::class.java)
//                                intent2.putExtra("Destroyer","Yes")
//                                startActivity(intent2)
//                                finish()
//                            }
//                        }
//
//
//                    }else{
//                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
//                    }
        }
    }

//    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                // You can use the Google account to sign in to Firebase Authentication
//                println(account.idToken)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Handle Google Sign-In failed
//
//                Toast.makeText(this,"Error : "+e, Toast.LENGTH_LONG).show()
//            }
//        }
//    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        // Use the idToken to authenticate with Firebase Authentication
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in successful, navigate to the main activity

                    val typeValue = intent.getStringExtra("type")
                    when (typeValue) {


                        "Individual" -> {
                            val intent2 =
                                Intent(this@loginactivity,MainActivityIndividual::class.java)
                            intent2.putExtra("Individual","Yes")
                            startActivity(intent2)
                            finish()
                        }

                        "Bank" -> {
                            val intent2 = Intent(this@loginactivity, MainActivityBank ::class.java)
                            intent2.putExtra("Corridor","No")
                            startActivity(intent2)
                            finish()
                        }
                        "Corridor" -> {
                            val intent2 = Intent(this@loginactivity, MainActivityCorridor ::class.java)
                            intent2.putExtra("Corridor","Yes")
                            startActivity(intent2)
                            finish()
                        }
                        "Recycler" -> {
                            val intent2 = Intent(this@loginactivity, MainActivityRecyclerDestroyer ::class.java)
                            intent2.putExtra("Destroyer","No")
                            startActivity(intent2)
                            finish()
                        }
                        else -> {
                            val intent2 = Intent(this@loginactivity, MainActivityRecyclerDestroyer ::class.java)
                            intent2.putExtra("Destroyer","Yes")
                            startActivity(intent2)
                            finish()
                        }
                    }
                } else {
                    // Sign-in failed
                    Toast.makeText(this@loginactivity,"Error Signing in !", Toast.LENGTH_LONG).show()
                }
            }
    }




}
