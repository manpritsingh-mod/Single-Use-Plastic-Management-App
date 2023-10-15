package com.manprit.plastic_management.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manprit.plastic_management.Database.RecyclerFormData
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.Interface.OnButtonClickListener
import com.manprit.plastic_management.Interface.OnButtonClicked
import com.manprit.plastic_management.MapsActivity
import com.manprit.plastic_management.PlasticCorridor.MainActivityCorridor
import com.manprit.plastic_management.PlasticRecyclerDestroyer.MainActivityDestroyer
import com.manprit.plastic_management.PlasticRecyclerDestroyer.MainActivityRecyclerDestroyer
import com.manprit.plastic_management.R



class RecycleStatusDestroyerAdapter (val context: Context, val itemList: ArrayList<RecyclerFormData>,val buttonclick : OnButtonClicked,val uid:String): RecyclerView.Adapter<RecycleStatusDestroyerAdapter.HomeViewHolder>() {

    lateinit var database: DatabaseReference

        class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtWeight: TextView = view.findViewById(R.id.textView)
            val txtMobile: TextView = view.findViewById(R.id.textView1)
            val txtTime: TextView = view.findViewById(R.id.textView2)
            val txtDate: TextView = view.findViewById(R.id.textView3)
            val txtStatus: TextView = view.findViewById(R.id.textView4)
            val txtSourceType: TextView = view.findViewById(R.id.textView6)
            val uid : TextView = view.findViewById(R.id.textView7)
            val btnAccepted : Button = view.findViewById(R.id.btnAccpetRequest)
            val btnRejected : Button = view.findViewById(R.id.btnRejectRequest)
            val address : TextView = view.findViewById(R.id.textView9)
            val name : TextView = view.findViewById(R.id.textView10)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_status_items_des, parent, false)
            return HomeViewHolder(view)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
            val detailsData = itemList[position]
                holder.txtSourceType.text = "Request From : ${detailsData.sourceType}"
                holder.uid.text = "UID : ${detailsData.uid}"
            holder.name.text = "Name : ${detailsData.Name}"
                holder.txtWeight.text = "Weight : ${detailsData.Weight} Kg"
                holder.txtMobile.text = "Mobile : ${detailsData.Phone}"
                holder.txtTime.text = "Time : ${detailsData.Time}"
                holder.txtDate.text = "Date : ${detailsData.Date}"
                holder.address.text = "Address : ${detailsData.Address}"
                holder.txtStatus.text = "Status : ${detailsData.Status}"
                holder.btnAccepted.setOnClickListener {

                    val requestStatus = "Accepted"

                    try {

                        database = FirebaseDatabase.getInstance()
                            .getReference("RequestRecyclerToDestroyer")

                        val user = RecyclerFormData(
                            detailsData.reqID,
                            detailsData.Name,
                            detailsData.Phone,
//                detailsData.District,
                            detailsData.DonationType,
                            detailsData.Address,
                            detailsData.Weight,
                            detailsData.Date,
                            detailsData.Time,
                            requestStatus,
                            detailsData.sourceType,
                            detailsData.uid,
                            uid
                        )

                        database.child(detailsData.reqID!!).setValue(user).addOnCompleteListener{
                            val dialog = AlertDialog.Builder(context)
                            dialog.setTitle("Accepted !")
                                .setMessage("Thank You for accepting the Requested ! ")
                                .setPositiveButton("OK") { text, listener ->
                                    buttonclick.onButtonClicked(
                                        Intent(
                                            context as Context,
                                            MainActivityDestroyer::class.java
                                        )
                                    )
                                }
                                .create()
                                .show()
                        }



                    } catch (e: FirebaseException) {
                        Toast.makeText(context as Context, "Error!! " + e, Toast.LENGTH_LONG).show()
                    }
                }

            holder.btnRejected.setOnClickListener {

                val requestStatus = "Rejected"

                try {

                    database = FirebaseDatabase.getInstance()
                        .getReference("RequestRecyclerToDestroyer")

                    val user = RecyclerFormData(
                        detailsData.reqID,
                        detailsData.Name,
                        detailsData.Phone,
//                detailsData.District,
                        detailsData.DonationType,
                        detailsData.Address,
                        detailsData.Weight,
                        detailsData.Date,
                        detailsData.Time,
                        requestStatus,
                        detailsData.sourceType,
                        detailsData.uid,
                        uid
                    )

                    database.child(detailsData.reqID!!).setValue(user).addOnCompleteListener{
                        val dialog = AlertDialog.Builder(context)
                        dialog.setTitle("Rejected !")
                            .setMessage("You have rejected the Requested ! ")
                            .setPositiveButton("OK") { text, listener ->
                                buttonclick.onButtonClicked(
                                    Intent(
                                        context as Context,
                                        MainActivityDestroyer::class.java
                                    )
                                )
                            }
                            .create()
                            .show()
                    }



                } catch (e: FirebaseException) {
                    Toast.makeText(context as Context, "Error!! " + e, Toast.LENGTH_LONG).show()
                }
            }
        }

}