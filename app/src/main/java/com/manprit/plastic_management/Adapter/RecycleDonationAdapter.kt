package com.manprit.plastic_management.Adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.manprit.plastic_management.Database.IndividualRequestFormData
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.Interface.OnButtonClickListener
import com.manprit.plastic_management.Interface.OnButtonClicked
import com.manprit.plastic_management.MainActivityIndividual
import com.manprit.plastic_management.PlasticBank.MainActivityBank
import com.manprit.plastic_management.R


class RecycleDonationAdapter(val context: Context, val itemList: ArrayList<IndividualRequestFormData>,val buttonClickListener: OnButtonClicked,val uid : String) :
    RecyclerView.Adapter<RecycleDonationAdapter.HomeViewHolder>() {

    lateinit var database: DatabaseReference

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtWeight: TextView = view.findViewById(R.id.textView)
        val txtMobile: TextView = view.findViewById(R.id.textView1)
        val txtTime: TextView = view.findViewById(R.id.textView2)
        val txtDate: TextView = view.findViewById(R.id.textView3)
        val txtStatus: TextView = view.findViewById(R.id.textView4)
        val textView5: TextView = view.findViewById(R.id.textView5)
        val txtSourceType : TextView = view.findViewById(R.id.textView6)
        val uid : TextView = view.findViewById(R.id.textView7)
        val btnAccpetRequest: Button = view.findViewById(R.id.btnAccpetRequest)
        val btnRejectRequest: Button = view.findViewById(R.id.btnRejectRequest)
        val address : TextView = view.findViewById(R.id.textView9)
        val name : TextView = view.findViewById(R.id.textView10)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.individual_bank_accept_reject, parent, false)
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
        holder.txtWeight.text = "Weight : ${detailsData.Weight}"
        holder.txtMobile.text = "Mobile : ${detailsData.Phone}"
        holder.txtTime.text = "Time : ${detailsData.Time}"
        holder.txtDate.text = "Date : ${detailsData.Date}"
        holder.address.text = "Address : ${detailsData.Address}"
        holder.txtStatus.text = "Status : ${detailsData.Status}"
        holder.textView5.text = "Near By Bank : ${detailsData.nearByBank}"

        val intent = Intent(context as Context, MainActivityBank::class.java)

        holder.btnAccpetRequest.setOnClickListener {
            val requestStatus = "Accepted"

            try {

                database = FirebaseDatabase.getInstance().getReference("RequestFormDeposit")

                val user = IndividualRequestFormData(
                    detailsData.reqID,
                    detailsData.Name,
                    detailsData.Email,
                    detailsData.Phone,
                    detailsData.Address,
//                detailsData.District,
                    detailsData.DonationType,
                    detailsData.Weight,
                    detailsData.nearByBank,
                    detailsData.Date,
                    detailsData.Time,
                    requestStatus,
                    detailsData.sourceType,
                    detailsData.uid,
                    uid,
                )

                database.child(detailsData.reqID!!).setValue(user).addOnSuccessListener {

                    val dialog = AlertDialog.Builder(context)
                    dialog.setTitle("Request Accepted !")
                        .setMessage("Thank You for accepting the request ! ")
                        .setPositiveButton("OK") { text, listener ->
                            buttonClickListener.onButtonClicked(intent)
                        }
                        .create()
                        .show()
                }


//            holder.btnAccpetRequest.isEnabled = false
//            holder.btnRejectRequest.isEnabled = false

            } catch (e: FirebaseException) {
                Toast.makeText(context as Context, "Error!! " + e, Toast.LENGTH_LONG).show()
            }
        }

            holder.btnRejectRequest.setOnClickListener {
                val requestStatus = "Rejected"

                try {
                    database = FirebaseDatabase.getInstance().getReference("RequestFormDeposit")

                    val user = IndividualRequestFormData(
                        detailsData.reqID,
                        detailsData.Name,
                        detailsData.Email,
                        detailsData.Phone,
                        detailsData.Address,
//                detailsData.District,
                        detailsData.DonationType,
                        detailsData.Weight,
                        detailsData.nearByBank,
                        detailsData.Date,
                        detailsData.Time,
                        requestStatus,
                        detailsData.sourceType,
                        detailsData.uid,
                        uid
                    )

                    database.child(detailsData.reqID!!).setValue(user).addOnSuccessListener {

                        val dialog = AlertDialog.Builder(context)
                        dialog.setTitle("Request Rejected !")
                            .setMessage("The  request has been rejected ! ")
                            .setPositiveButton("OK") { text, listener ->
                                buttonClickListener.onButtonClicked(intent)
                            }
                            .create()
                            .show()
                    }

//            holder.btnAccpetRequest.isEnabled = false
//            holder.btnRejectRequest.isEnabled = false



                } catch (e: FirebaseException) {
                    Toast.makeText(context as Context, "Error!! " + e, Toast.LENGTH_LONG).show()
                }
            }



    }

}