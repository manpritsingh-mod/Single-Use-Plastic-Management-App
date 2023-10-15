package com.manprit.plastic_management.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.manprit.plastic_management.Adapter.RecyclerRequestAdapter
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Interface.OnButtonClickListener
import com.manprit.plastic_management.Interface.OnButtonClicked
import com.manprit.plastic_management.MapsActivity
import com.manprit.plastic_management.R
import java.text.SimpleDateFormat
import java.util.Locale

class popupRequest : Fragment(), OnButtonClickListener,OnButtonClicked {

    lateinit var recyclerLayout : RecyclerView
    lateinit var popupReq : CardView

    lateinit var database: DatabaseReference
    lateinit var sharedPreferences : SharedPreferences

    override fun onButtonClicked(intent: Intent) {
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onButtonClicked(data: RequestDetailsData) {
        // Launch the activity using startActivity
        if(data.latitude.equals("N/A") || data.longitude.equals("N/A")){
            val dialog = AlertDialog.Builder(context as Context)
            dialog.setTitle("Location Error !")
                .setMessage("Map Location for the request is not available !")
                .setPositiveButton("OK") { text, listener ->

                }
                .create()
                .show()
        }
        else{
        val intent = Intent(context as Context, MapsActivity::class.java)
        intent.putExtra("latitide",data.latitude)
        intent.putExtra("longitude",data.longitude)
        startActivity(intent)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =inflater.inflate(R.layout.fragment_popup_request, container, false)

        val requestDetailsDataArray = arrayListOf<RequestDetailsData>()

        popupReq = view.findViewById(R.id.popupReqCard)

        val clicklistener = this
        val btnClick = this

        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("Uid", "ID")

        recyclerLayout = view.findViewById(R.id.recyclerHome)
        recyclerLayout.layoutManager = LinearLayoutManager(activity)
        recyclerLayout.adapter = RecyclerRequestAdapter(activity as Context, requestDetailsDataArray,btnClick,clicklistener,uid!!)


        try {
            database = FirebaseDatabase.getInstance().getReference("RequestFormCollection")

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapShot in snapshot.children) {
                            if (dataSnapShot.key != "test") {
                                val detailsData =
                                    dataSnapShot.getValue(RequestDetailsData::class.java)
                                if (detailsData!!.Status.toString().equals("Pending"))
                                    if (!requestDetailsDataArray.contains(detailsData))
                                        requestDetailsDataArray.add(detailsData!!)
                            }
                        }

                        recyclerLayout.layoutManager = LinearLayoutManager(activity)
//                        recyclerLayout.adapter = RecyclerRequestAdapter(
//                            activity as Context,
//                            requestDetailsDataArray,
//                            btnClick,
//                            clicklistener
//                        )

                        requestDetailsDataArray.sortWith(compareByDescending<RequestDetailsData> {
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                        }.thenByDescending {
                            SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                        })

                        recyclerLayout?.adapter = activity?.let { RecyclerRequestAdapter(it, requestDetailsDataArray,btnClick, clicklistener, uid) }


                        if (requestDetailsDataArray.isNotEmpty()) {
                            popupReq.visibility = View.VISIBLE
                        }


                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        activity as Context, error.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
        catch (e: FirebaseException) {
            Toast.makeText(context as Context, "Error!! " + e, Toast.LENGTH_LONG).show()
        }
        return view
    }



}