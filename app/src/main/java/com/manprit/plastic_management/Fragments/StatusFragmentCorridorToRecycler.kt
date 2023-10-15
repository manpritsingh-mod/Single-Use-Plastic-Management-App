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
import com.manprit.plastic_management.Adapter.RecycleHistoryAdapterCorridor
import com.manprit.plastic_management.Adapter.RecycleHistoryAdapterCorridorToRecycler
import com.manprit.plastic_management.Adapter.RecycleStatusAdapter
import com.manprit.plastic_management.Adapter.RecycleStatusCorridorAdapter
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.Interface.OnButtonClickListener
import com.manprit.plastic_management.Interface.OnButtonClicked
import com.manprit.plastic_management.MapsActivity
import com.manprit.plastic_management.R
import java.text.SimpleDateFormat
import java.util.Locale


class StatusFragmentCorridorToRecycler() : Fragment() {

    lateinit var recyclerLayout : RecyclerView
    lateinit var historyCard : CardView

    lateinit var database2: DatabaseReference

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val statusDetailDataArray = arrayListOf<RequestDetailsData>()

        historyCard = view.findViewById(R.id.historyCard)

        recyclerLayout = view.findViewById(R.id.recyclerHistory)
        recyclerLayout.layoutManager = LinearLayoutManager(activity)
        recyclerLayout.adapter = RecycleHistoryAdapterCorridorToRecycler(activity as Context, statusDetailDataArray)

        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("Uid", "ID")


        try {

            database2 = FirebaseDatabase.getInstance().getReference("RequestToRecycler")

            database2.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapShot in snapshot.children) {
                            if (dataSnapShot.key != "test") {
                                val detailsData =
                                    dataSnapShot.getValue(RequestDetailsData::class.java)
                                if(detailsData!!.uid.toString().equals(uid))
                                    if(detailsData.Status.toString().equals("Pending"))
                                    if (!statusDetailDataArray.contains(detailsData))
                                        statusDetailDataArray.add(detailsData!!)
                            }
                        }

                        recyclerLayout.layoutManager = LinearLayoutManager(activity)
//                        recyclerLayout.adapter =
//                            RecycleHistoryAdapter(activity as Context, statusDetailDataArray)

                        statusDetailDataArray.sortWith(compareByDescending<RequestDetailsData> {
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                        }.thenByDescending {
                            SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                        })

                        recyclerLayout?.adapter = activity?.let { RecycleHistoryAdapterCorridorToRecycler(it, statusDetailDataArray) }

                        if (statusDetailDataArray.isNotEmpty()) {
                            historyCard.visibility = View.VISIBLE
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