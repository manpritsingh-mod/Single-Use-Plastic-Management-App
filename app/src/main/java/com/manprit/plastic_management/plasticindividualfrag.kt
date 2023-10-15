package com.manprit.plastic_management

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.manprit.plastic_management.loginSignup.loginactivity

class plasticindividualfrag : Fragment() {

    lateinit var nextBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_plastic_individualfrag, container, false)

        nextBtn = view.findViewById(R.id.button3)

        nextBtn.setOnClickListener {

            val intent = Intent(context as Context , com.manprit.plastic_management.loginSignup.loginactivity::class.java)
           intent.putExtra("type","Individual")
            startActivity(intent)
                activity?.finish()

        }

        return view
    }

}
