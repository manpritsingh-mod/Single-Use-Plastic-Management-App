package com.manprit.plastic_management.Fragments

import android.content.Context
import android.content.Intent
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
import com.manprit.plastic_management.Adapter.RecycleNewRequestAdapter
import com.manprit.plastic_management.Adapter.RecycleStatusAdapter
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.MapsActivity
import com.manprit.plastic_management.R
import java.text.SimpleDateFormat
import java.util.Locale


class NewRequestsFragment(var type : String?) : Fragment() {

    lateinit var recyclerLayout : RecyclerView
    lateinit var statusCard : CardView

    lateinit var database: DatabaseReference

    lateinit var path : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_status, container, false)

        val statusDetailDataArray = arrayListOf<RequestDetailsData>()

        statusCard = view.findViewById(R.id.statusCard)
        statusCard.visibility = View.VISIBLE

        recyclerLayout = view.findViewById(R.id.recyclerStatus)
        recyclerLayout.layoutManager = LinearLayoutManager(activity)
        recyclerLayout.adapter = RecycleNewRequestAdapter(activity as Context, statusDetailDataArray)

        if(type.toString().equals("Recycler")){
            path = "RequestToRecycler"
        }else{
            path = "RequestFormCollection"
        }

        try {
            database = FirebaseDatabase.getInstance().getReference(path)


            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapShot in snapshot.children) {
                            if (dataSnapShot.key != "test") {

                                val detailsData =
                                    dataSnapShot.getValue(RequestDetailsData::class.java)
                                if (detailsData!!.Status.toString().equals("Pending"))
                                    if (!statusDetailDataArray.contains(detailsData))
                                        statusDetailDataArray.add(detailsData!!)
                            }
                        }

                        recyclerLayout.layoutManager = LinearLayoutManager(activity)
//                        recyclerLayout.adapter =
//                            RecycleNewRequestAdapter(activity as Context, statusDetailDataArray)

                        statusDetailDataArray.sortWith(compareByDescending<RequestDetailsData> {
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                        }.thenByDescending {
                            SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                        })

                        recyclerLayout?.adapter = activity?.let { RecycleNewRequestAdapter(it, statusDetailDataArray) }

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