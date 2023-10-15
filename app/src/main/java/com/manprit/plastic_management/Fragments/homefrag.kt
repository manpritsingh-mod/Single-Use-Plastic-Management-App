package com.manprit.plastic_management.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manprit.plastic_management.Form.form_Bank_Corridor
import com.manprit.plastic_management.PlasticBank.History
import com.manprit.plastic_management.PlasticBank.CurrentStatus
import com.manprit.plastic_management.PlasticBank.RequestCollection
import com.manprit.plastic_management.R
import com.manprit.plastic_management.UserSelection
import com.manprit.plastic_management.loginSignup.loginactivity


class homefrag() : Fragment() {

    private lateinit var logoutBtn: ImageView
    private lateinit var requestBtn: ImageView
    private lateinit var statusBtn: ImageView
    private lateinit var historyBtn: ImageView
//    lateinit var btnRequest : ImageView
    lateinit var btnDeposit : Button
    lateinit var txtusername: TextView
    lateinit var ScreenType : TextView

    lateinit var typeVal : String


    constructor(value: String) : this() {
        // Handle the argument as needed
        typeVal = value
    }

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        logoutBtn = view.findViewById(R.id.btnLogout)
        requestBtn = view.findViewById(R.id.btnRequest)
        statusBtn = view.findViewById(R.id.btnCurrentStatus)
        historyBtn = view.findViewById(R.id.btnHistory)
//        btnRequest = view.findViewById(R.id.btnReq)
        btnDeposit = view.findViewById(R.id.PlasticBankcard2)
        txtusername = view.findViewById(R.id.txtusername)
        ScreenType = view.findViewById(R.id.ScreenType)



        ScreenType.text = "Plastic Bank"

        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("UserName", null)
        txtusername.text = username


        logoutBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(context as Context)
            dialog.setTitle("Logout")
            dialog.setMessage("Are you sure you want to logout ? ")
            dialog.setPositiveButton("YES"){ text , listener ->
                sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
                startActivity(Intent(context, UserSelection::class.java))
                activity?.finish()
            }
            dialog.setNegativeButton("NO"){text , listener ->  }
            dialog.create()
            dialog.show()
        }

        requestBtn.setOnClickListener {

            startActivity(Intent(requireContext(), RequestCollection::class.java))
        }
        statusBtn.setOnClickListener {

            startActivity(Intent(requireContext(), CurrentStatus::class.java))
        }
        historyBtn.setOnClickListener {

            startActivity(Intent(requireContext(), History::class.java))
        }

//        btnRequest.setOnClickListener {
//            val intent = Intent(context as Context , form_Bank_Corridor::class.java)
//
//            if(typeVal=="Yes")
//            {
//                intent.putExtra("Remove",true)
//            }else{
//                intent.putExtra("Remove",false)
//            }
//            intent.putExtra("Title","Plastic Deposit Form")
//
//            startActivity(intent)
//        }

        btnDeposit.setOnClickListener {
            val intent = Intent(context as Context , form_Bank_Corridor::class.java)
            intent.putExtra("Remove",true)
            startActivity(intent)
        }

        return view
    }

}