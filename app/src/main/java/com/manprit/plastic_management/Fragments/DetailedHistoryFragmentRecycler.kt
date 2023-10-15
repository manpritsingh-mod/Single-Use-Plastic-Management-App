package com.manprit.plastic_management.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.manprit.plastic_management.Adapter.RecycleDetailedHistoryAdapter
import com.manprit.plastic_management.Adapter.RecycleDetailedHistoryCorridorAdapter
import com.manprit.plastic_management.Adapter.RecycleDetailedHistoryIndividualAdapter
import com.manprit.plastic_management.Adapter.RecycleDetailedHistoryRecyclerAdapter
import com.manprit.plastic_management.Adapter.RecycleHistoryAdapter
import com.manprit.plastic_management.Adapter.RecycleStatusAdapter
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.MapsActivity
import com.manprit.plastic_management.R
import java.text.SimpleDateFormat
import java.util.Locale

class DetailedHistoryFragmentRecycler : Fragment() {

    lateinit var recyclerLayout : RecyclerView
    lateinit var historyCard : CardView
    lateinit var titleText : TextView

    lateinit var database: DatabaseReference
    lateinit var database2: DatabaseReference
    lateinit var database3: DatabaseReference
    lateinit var database4: DatabaseReference
    lateinit var database5: DatabaseReference
    lateinit var database6: DatabaseReference
    lateinit var database7: DatabaseReference

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val statusDetailDataArray = arrayListOf<RequestDetailsData>()

        historyCard = view.findViewById(R.id.historyCard)
        titleText = view.findViewById(R.id.succesfulReq)
        

        recyclerLayout = view.findViewById(R.id.recyclerHistory)
        recyclerLayout.layoutManager = LinearLayoutManager(activity)
        recyclerLayout.adapter = RecycleDetailedHistoryRecyclerAdapter(activity as Context, statusDetailDataArray)

        val layoutParams = recyclerLayout.layoutParams as ViewGroup.MarginLayoutParams
        val marginTopInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50.toFloat(), resources.displayMetrics)
        layoutParams.topMargin = marginTopInPixels.toInt()

        // Set the updated LayoutParams back to the view
        recyclerLayout.layoutParams = layoutParams
        titleText.visibility = View.VISIBLE

        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_file), Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("Uid", "ID")

       try {
           database = FirebaseDatabase.getInstance().getReference("RequestToRecycler")
           database2 = FirebaseDatabase.getInstance().getReference("RequestRecyclerToDestroyer")


           database.addValueEventListener(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists()) {
                       for (dataSnapShot in snapshot.children) {
                           if (dataSnapShot.key != "test") {
                               val detailsData =
                                   dataSnapShot.getValue(RequestDetailsData::class.java)

                               if (!(detailsData!!.Status.toString()
                                       .equals("Pending")||detailsData!!.Status.toString()
                                       .equals("Rejected"))
                               )

                                   if (!statusDetailDataArray.contains(detailsData))
                                       statusDetailDataArray.add(detailsData!!)
                           }
                       }

                       recyclerLayout.layoutManager = LinearLayoutManager(activity)
//                       recyclerLayout.adapter =
//                           RecycleHistoryAdapter(activity as Context, statusDetailDataArray)

                       statusDetailDataArray.sortWith(compareByDescending<RequestDetailsData> {
                           SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                       }.thenByDescending {
                           SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                       })

                       recyclerLayout?.adapter = activity?.let { RecycleDetailedHistoryRecyclerAdapter(it, statusDetailDataArray) }


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

           database2.addValueEventListener(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists()) {
                       for (dataSnapShot in snapshot.children) {
                           if (dataSnapShot.key != "test") {
                               val detailsData =
                                   dataSnapShot.getValue(RequestDetailsData::class.java)
                               if (!(detailsData!!.Status.toString()
                                       .equals("Pending") || detailsData!!.Status.toString()
                                       .equals("Rejected") )
                               )
                                   if (!statusDetailDataArray.contains(detailsData))
                                       statusDetailDataArray.add(detailsData!!)
                           }
                       }

                       recyclerLayout.layoutManager = LinearLayoutManager(activity)

                       statusDetailDataArray.sortWith(compareByDescending<RequestDetailsData> {
                           SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                       }.thenByDescending {
                           SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                       })

                       recyclerLayout?.adapter = activity?.let { RecycleDetailedHistoryRecyclerAdapter(it, statusDetailDataArray) }

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

           database3 = FirebaseDatabase.getInstance().getReference("RequestFormDeposit")


           database3.addValueEventListener(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists()) {
                       for (dataSnapShot in snapshot.children) {
                           if (dataSnapShot.key != "test") {
                               val detailsData =
                                   dataSnapShot.getValue(RequestDetailsData::class.java)

                               if ((detailsData!!.Status.toString()
                                       .equals("Completed") || detailsData!!.Status.toString()
                                       .equals("Accepted") )
                               )

                                   if (!statusDetailDataArray.contains(detailsData))
                                       statusDetailDataArray.add(detailsData!!)
                           }
                       }

                       recyclerLayout.layoutManager = LinearLayoutManager(activity)
//                       recyclerLayout.adapter =
//                           RecycleHistoryAdapter(activity as Context, statusDetailDataArray)

                       statusDetailDataArray.sortWith(compareByDescending<RequestDetailsData> {
                           SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                       }.thenByDescending {
                           SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                       })

                       recyclerLayout?.adapter = activity?.let { RecycleDetailedHistoryRecyclerAdapter(it, statusDetailDataArray) }


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

           database4 = FirebaseDatabase.getInstance().getReference("RequestFormCollection")

           database4.addValueEventListener(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists()) {
                       for (dataSnapShot in snapshot.children) {
                           if (dataSnapShot.key != "test") {
                               val detailsData =
                                   dataSnapShot.getValue(RequestDetailsData::class.java)

                               if ((detailsData!!.Status.toString()
                                       .equals("Completed") || detailsData!!.Status.toString()
                                       .equals("Accepted") )
                               )

                                   if (!statusDetailDataArray.contains(detailsData))
                                       statusDetailDataArray.add(detailsData!!)
                           }
                       }

                       recyclerLayout.layoutManager = LinearLayoutManager(activity)
//                       recyclerLayout.adapter =
//                           RecycleHistoryAdapter(activity as Context, statusDetailDataArray)

                       statusDetailDataArray.sortWith(compareByDescending<RequestDetailsData> {
                           SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.Date)
                       }.thenByDescending {
                           SimpleDateFormat("HH:mm", Locale.getDefault()).parse(it.Time)
                       })

                       recyclerLayout?.adapter = activity?.let { RecycleDetailedHistoryRecyclerAdapter(it, statusDetailDataArray) }


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