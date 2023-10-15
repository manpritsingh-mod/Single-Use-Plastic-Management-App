package com.manprit.plastic_management.OnBoardingScren


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manprit.plastic_management.R


class OnboardingIteamAdapter(private val onBoardingItems: List<OnBoardingItem>):
        RecyclerView.Adapter<OnboardingIteamAdapter.OnbordingItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnbordingItemViewHolder {
        return OnbordingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: OnbordingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnbordingItemViewHolder (view: View) : RecyclerView.ViewHolder(view){
                private val imageOnboarding = view.findViewById<ImageView>( R.id.imageOnboarding)
                private val textTitle = view.findViewById<TextView>( R.id.textTitle)
                private val textDescription = view.findViewById<TextView>( R.id.textDescription)

                fun bind(onBoardingItem: OnBoardingItem){

                    imageOnboarding.setImageResource(onBoardingItem.onboardingImage)
                    textTitle.text = onBoardingItem.title
                    textDescription.text = onBoardingItem.description
                }

    }
}