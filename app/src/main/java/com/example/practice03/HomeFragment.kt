package com.example.practice03

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.homefragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener()
    }

    private fun clickListener() {
        val moneyCardView = requireView().findViewById<CardView>(R.id.Money_CardView)
        val lifeCardview = requireView().findViewById<CardView>(R.id.Life_CardView)
        val travelCardView = requireView().findViewById<CardView>(R.id.Travel_CardView)
        val employmentCardView = requireView().findViewById<CardView>(R.id.Employment_CardView)

        moneyCardView.setOnClickListener {
            openMoneyDetails()
        }

        lifeCardview.setOnClickListener {
            openLifeDetails()
        }

        travelCardView.setOnClickListener {
            openTravelDetails()
        }

        employmentCardView.setOnClickListener {
            openEmploymentDetails()
        }
    }

    private fun openMoneyDetails() {
        startActivity(Intent(requireActivity(), MoneyDetails::class.java))
    }

    private fun openLifeDetails() {
        startActivity(Intent(requireActivity(), LifeDetails::class.java))
    }

    private fun openTravelDetails() {
        startActivity(Intent(requireActivity(), TravelDetails::class.java))
    }

    private fun openEmploymentDetails() {
        startActivity(Intent(requireActivity(), EmploymentDetails::class.java))
    }
}








