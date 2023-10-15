package com.manprit.plastic_management.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.manprit.plastic_management.Form.form_Recycler_Destroyer
import com.manprit.plastic_management.PlasticBank.History
import com.manprit.plastic_management.PlasticBank.RequestCollection
import com.manprit.plastic_management.PlasticRecyclerDestroyer.CurrentReqDetailsDes
import com.manprit.plastic_management.PlasticRecyclerDestroyer.DestroyerHistory
import com.manprit.plastic_management.R
import com.manprit.plastic_management.UserSelection
import com.manprit.plastic_management.loginSignup.loginactivity


class homefrag2Des() : Fragment() {


    private lateinit var logoutBtn: ImageView
    private lateinit var requestBtn: ImageView
    private lateinit var historyBtn: ImageView



    lateinit var type : String

    constructor(value: String) : this() {
        // Handle the argument as needed
        type = value
    }


    lateinit var sharedPreferences : SharedPreferences
    lateinit var txtusername: TextView
    lateinit var ScreenType : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_homefrag2_des, container, false)

        logoutBtn = view.findViewById(R.id.btnLogout)
        requestBtn = view.findViewById(R.id.btnRequest)
        historyBtn = view.findViewById(R.id.btnHistory)
        txtusername = view.findViewById(R.id.txtusername)
//        txtView = view.findViewById(R.id.txtView1)
        ScreenType = view.findViewById(R.id.ScreenType)

        ScreenType.text = "Plastic Destroyer"



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

            startActivity(Intent(context as Context, CurrentReqDetailsDes::class.java).putExtra("Type",type))

        }

        historyBtn.setOnClickListener {

            startActivity(Intent(context as Context, DestroyerHistory::class.java).putExtra("Type",type))
        }

        return view
    }


}