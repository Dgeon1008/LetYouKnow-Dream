package com.example.practice03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import android.content.Intent
import android.net.Uri

class TravelDetailActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var eligibilityTextView: TextView
    private lateinit var deadlineTextView: TextView
    private lateinit var requirementsTextView: TextView
    private lateinit var submitDocsTextView: TextView
    private lateinit var agencyTextView: TextView
    private lateinit var receptionAgencyTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var applySiteTextView: TextView
    private lateinit var referenceSiteTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.travel_details_activity)

        // Firestore 인스턴스 초기화 및 설정
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        eligibilityTextView = findViewById(R.id.eligibilityTextView)
        deadlineTextView = findViewById(R.id.deadlineTextView)
        requirementsTextView = findViewById(R.id.requirementsTextView)
        submitDocsTextView = findViewById(R.id.submitDocsTextView)
        agencyTextView = findViewById(R.id.agencyTextView)
        receptionAgencyTextView = findViewById(R.id.receptionAgencyTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        applySiteTextView = findViewById(R.id.applySiteTextView)
        referenceSiteTextView = findViewById(R.id.referenceSiteTextView)

        val benefit = intent.getStringExtra("benefit")

        if (benefit != null) {
            fetchDocument(benefit)
        } else {
            Log.d("TravelDetailActivity", "No benefit name provided")
        }

        referenceSiteTextView.setOnClickListener {
            val url = referenceSiteTextView.text.toString()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        applySiteTextView.setOnClickListener {
            val url = applySiteTextView.text.toString()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    private fun fetchDocument(benefitName: String) {
        val documentReference = db.collection("여행").document(benefitName)

        documentReference.get(Source.DEFAULT)
            .addOnSuccessListener { document ->
                if (document != null) {
                    titleTextView.text = document.id
                    descriptionTextView.text = document.getString("지원내용")?.replace("\\n", "\n")
                    eligibilityTextView.text = document.getString("지원대상")?.replace("\\n", "\n")
                    deadlineTextView.text = document.getString("신청기간")?.replace("\\n", "\n")
                    requirementsTextView.text = document.getString("신청방법")?.replace("\\n", "\n")
                    submitDocsTextView.text = document.getString("제출서류 및 발급처")?.replace("\\n", "\n")
                    agencyTextView.text = document.getString("주관기관")?.replace("\\n", "\n")
                    receptionAgencyTextView.text = document.getString("운영기관")?.replace("\\n", "\n")
                    phoneTextView.text = document.getString("전화문의")?.replace("\\n", "\n")
                    applySiteTextView.text = document.getString("신청 사이트")?.replace("\\n", "\n")
                    referenceSiteTextView.text = document.getString("참고 사이트")?.replace("\\n", "\n")
                } else {
                    Log.d("TravelDetailActivity", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TravelDetailActivity", "get failed with ", exception)
                if (exception is FirebaseFirestoreException) {
                    when (exception.code) {
                        FirebaseFirestoreException.Code.UNAVAILABLE -> {
                            Log.d("TravelDetailActivity", "Firestore service is unavailable. Check your internet connection.")
                        }
                        else -> {
                            Log.d("TravelDetailActivity", "An unknown error occurred.")
                        }
                    }
                }
            }
    }
}






