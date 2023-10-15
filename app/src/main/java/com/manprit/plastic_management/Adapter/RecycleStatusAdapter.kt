package com.manprit.plastic_management.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.manprit.plastic_management.Database.RequestDetailsData
import com.manprit.plastic_management.Database.RequestFormData
import com.manprit.plastic_management.Interface.OnButtonClickListener
import com.manprit.plastic_management.MapsActivity
import com.manprit.plastic_management.R



class RecycleStatusAdapter (val context: Context, val itemList: ArrayList<RequestDetailsData>,val buttonClickListener: OnButtonClickListener): RecyclerView.Adapter<RecycleStatusAdapter.HomeViewHolder>() {

        class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtWeight: TextView = view.findViewById(R.id.textView)
            val txtMobile: TextView = view.findViewById(R.id.textView1)
            val txtTime: TextView = view.findViewById(R.id.textView2)
            val txtDate: TextView = view.findViewById(R.id.textView3)
            val txtStatus: TextView = view.findViewById(R.id.textView4)
            val btnLoc : Button = view.findViewById(R.id.btnLocation)
            val txtSourceType: TextView = view.findViewById(R.id.textView6)
            val uid : TextView = view.findViewById(R.id.textView7)
            val address : TextView = view.findViewById(R.id.textView9)
            val name : TextView = view.findViewById(R.id.textView10)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_status_items, parent, false)
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
                holder.txtWeight.text = "Weight : ${detailsData.Weight} "
                holder.txtMobile.text = "Mobile : ${detailsData.Phone}"
                holder.txtTime.text = "Time : ${detailsData.Time}"
                holder.txtDate.text = "Date : ${detailsData.Date}"
                holder.address.text = "Address : ${detailsData.Address}"
                holder.txtStatus.text = "Status : ${detailsData.Status}"
                holder.btnLoc.setOnClickListener {
                    buttonClickListener.onButtonClicked(detailsData)
                }

        }

}