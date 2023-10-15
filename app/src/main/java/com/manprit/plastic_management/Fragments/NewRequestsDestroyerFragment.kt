package com.manprit.plastic_management.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import com.manprit.plastic_management.Adapter.RecycleNewRequestDestroyerAdapter
import com.manprit.plastic_management.Adapter.RecycleStatusAdapter
import com.manprit.plastic_management.Database.RecyclerFormData
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.MapsActivity
import com.manprit.plastic_management.R
import java.text.SimpleDateFormat
import java.util.Locale


class NewRequestsDestroyerFragment() : Fragment() {

    lateinit var recyclerLayout : RecyclerView
    lateinit var statusCard : CardView

    lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_status, container, false)

        val reqDetailDataArray = arrayListOf<RecyclerFormData>()

        statusCard = view.findViewById(R.id.statusCard)
        statusCard.visibility = View.VISIBLE

        recyclerLayout = view.findViewById(R.id.recyclerStatus)
        recyclerLayout.layoutManager = LinearLayoutManager(activity)
        recyclerLayout.adapter = RecycleNewRequestDestroyerAdapter(activity as Context, reqDetailDataArray)

        try {

            database = FirebaseDatabase.getInstance().getReference("RequestRecyclerToDestroyer")

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapShot in snapshot.children) {
                            if (dataSnapShot.key != "test") {
                                val detailsData =
                                    dataSnapShot.getValue(RecyclerFormData::class.java)
                                if (detailsData!!.Status.toString().equals("Pending"))
                                if (!reqDetailDataArray.contains(detailsData))
                                    reqDetailDataArray.add(detailsData!!)
                            }
                        }



                        recyclerLayout.layoutManager = LinearLayoutManager(activity)
//                        recyclerLayout.adapter = RecycleNewRequestDestroyerAdapter(
//                            activity as Context,
//                            reqDetailDataArray
//                        )

                        reqDetailDataArray.sortWith(compareByDescending<RecyclerFormData> {
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                        }.thenByDescending {
                            SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                        })

                        recyclerLayout?.adapter = activity?.let { RecycleNewRequestDestroyerAdapter(it, reqDetailDataArray) }


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