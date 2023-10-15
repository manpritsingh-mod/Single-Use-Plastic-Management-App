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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.manprit.plastic_management.Adapter.IndividualHistoryAdapter
import com.manprit.plastic_management.Adapter.RecycleStatusAdapter
import com.manprit.plastic_management.Database.IndividualRequestFormData
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.Interface.OnButtonClickListener
import com.manprit.plastic_management.MapsActivity
import com.manprit.plastic_management.R
import java.text.SimpleDateFormat
import java.util.Locale


class StatusFragment() : Fragment(), OnButtonClickListener {

    lateinit var recyclerLayout : RecyclerView
    lateinit var statusCard : CardView

    lateinit var database: DatabaseReference

    lateinit var sharedPreferences : SharedPreferences

    override fun onButtonClicked(data: RequestDetailsData) {
        // Launch the activity using startActivity
        val intent = Intent(context as Context, MapsActivity::class.java)
        intent.putExtra("latitide",data.latitude)
        intent.putExtra("longitude",data.longitude)
        startActivity(intent)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_status, container, false)

        val statusDetailDataArray = arrayListOf<RequestDetailsData>()

        statusCard = view.findViewById(R.id.statusCard)

        recyclerLayout = view.findViewById(R.id.recyclerStatus)
        recyclerLayout.layoutManager = LinearLayoutManager(activity)
        recyclerLayout.adapter = RecycleStatusAdapter(activity as Context, statusDetailDataArray,this)
        val clickListiner = this

        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("Uid", "ID")

        try {
            database = FirebaseDatabase.getInstance().getReference("RequestFormCollection")


            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        for (dataSnapShot in snapshot.children) {
                            if (dataSnapShot.key != "test") {
                                val detailsData =
                                    dataSnapShot.getValue(RequestDetailsData::class.java)
                                if (!(detailsData!!.Status.equals("Completed") || detailsData!!.Status.equals(
                                        "Accepted"
                                    ) || detailsData!!.Status.equals(
                                        "Rejected"
                                    ))
                                )
                                    if(detailsData!!.uid.toString().equals(uid))
                                    if (!statusDetailDataArray.contains(detailsData))
                                        statusDetailDataArray.add(detailsData!!)
                            }
                        }

                        recyclerLayout.layoutManager = LinearLayoutManager(activity)
//                        recyclerLayout.adapter = RecycleStatusAdapter(activity as Context, statusDetailDataArray,
//                            clickListiner
//                        )

                        statusDetailDataArray.sortWith(compareByDescending<RequestDetailsData> {
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                        }.thenByDescending {
                            SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                        })

                        recyclerLayout?.adapter = activity?.let { RecycleStatusAdapter(it, statusDetailDataArray,clickListiner) }


                        if (statusDetailDataArray.isNotEmpty()) {
                            statusCard.visibility = View.VISIBLE
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