package com.manprit.plastic_management.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.Database.Users
import com.manprit.plastic_management.R
import com.manprit.plastic_management.Util.isValidEmail

class detailsfrag : Fragment() {

    lateinit var name: TextView
    lateinit var firstName: EditText
    lateinit var email: EditText
    lateinit var phone: EditText
    lateinit var pass: EditText
    lateinit var btnUpdate: Button
    lateinit var ivPwd: ImageView
    lateinit var lastName : EditText
    lateinit var textView15: TextView

    lateinit var sharedPreferences: SharedPreferences
    lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detailsfrag, container, false)

        firstName = view.findViewById(R.id.etFullName)
        lastName = view.findViewById(R.id.etLastName1)
        name = view.findViewById(R.id.textView14)
        email = view.findViewById(R.id.etEmail)
        phone = view.findViewById(R.id.etPhone)
        pass = view.findViewById(R.id.etPass)
        btnUpdate = view.findViewById(R.id.button_update)
        ivPwd = view.findViewById(R.id.ivPwd)
        textView15 = view.findViewById(R.id.textView15)

        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.shared_pref_file),
            Context.MODE_PRIVATE
        )

        val name1 = sharedPreferences.getString("Name", "Full")
        val name2 = sharedPreferences.getString("Name", "User")
        val email1 = sharedPreferences.getString("Email", "Email Id")
        val phone1 = sharedPreferences.getString("Mobile", "Phone No")
        val lname1 = sharedPreferences.getString("LastName", "Name")
        val pass1 = sharedPreferences.getString("Password", "Password")
        val type = sharedPreferences.getString("Type", null)
        val username = sharedPreferences.getString("UserName", null)
        val uid = sharedPreferences.getString("Uid", "ID")

        name.text = name2
        firstName.setText(name1)
        lastName.setText(lname1)
        email.setText(email1)
        phone.setText(phone1)
        pass.setText(pass1)
        textView15.setText("("+uid+")")

        btnUpdate.setOnClickListener {



            try {
                database = FirebaseDatabase.getInstance().getReference("User/" + type)

                var email3 = email.text.toString()
                var phone3 = phone.text.toString()
                var pass3 = pass.text.toString()
                var firstname = firstName.text.toString()
                var lastname = lastName.text.toString()

                if (firstname.equals("") || lastname.equals("")  || pass3.equals("") || email3.equals("") || phone3.equals("")
                ) {
                    Toast.makeText(context, "Empty Fields !", Toast.LENGTH_LONG).show()
                }else if(!email3!!.isValidEmail()){
                    val dialog = AlertDialog.Builder(context)
                    dialog.setTitle("Invalid Email Format !")
                        .setMessage("Email should be in the following format :\n\"yourmail123@gmail.com\"")
                        .setPositiveButton("OK") { text, listener ->

                        }
                        .create()
                        .show()
                }
                else{

                val user = Users(
                    firstname, lastname, username!!, email3, phone3, pass3,uid!!
                )

                database.child(username!!).setValue(user)

                sharedPreferences.getString("Name", firstname)
                sharedPreferences.getString("Email", email3)
                sharedPreferences.getString("Mobile", phone3)
                sharedPreferences.getString("LastName", lastname)
                sharedPreferences.getString("Password", pass3)

                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("Success !")
                    .setMessage("Details successfully updated !")
                    .setPositiveButton("OK") { text, listener ->

                    }
                    .create()
                    .show()
            }
            }
            catch (e: FirebaseException) {
                Toast.makeText(context as Context, "Error!! " +e, Toast.LENGTH_LONG).show()
            }

        }

        ivPwd.setOnClickListener {
            if (pass.transformationMethod == PasswordTransformationMethod.getInstance()) {
                ivPwd.setImageResource(R.drawable. ic_eye_off_line)
                pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                pass.setSelection(pass.text.length)
            } else {
                ivPwd.setImageResource(R.drawable.ic_eye_line)
                pass.transformationMethod = PasswordTransformationMethod.getInstance()
                pass.setSelection(pass.text.length)
            }
        }


        return view
    }

}