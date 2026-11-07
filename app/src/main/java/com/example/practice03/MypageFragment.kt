package com.example.practice03

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class MypageFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.mypagefragment, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        // Get the current user
        val user = firebaseAuth.currentUser
        val email = user?.email

        // Find the TextView and set the email
        val textView: TextView = view.findViewById(R.id.textView)
        textView.text = "반가워요, $email 님"

        // Set the logout button action
        val logoutButton: Button = view.findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(activity, LoginPage::class.java)
            startActivity(intent)
            activity?.finish()
        }

        // Set the password change button action
        val passwordChangeButton: Button = view.findViewById(R.id.password_change)
        passwordChangeButton.setOnClickListener {
            val intent = Intent(activity, Changepwd::class.java)
            startActivity(intent)
        }

        // Set the favorite benefits button action
        val favoriteButton: Button = view.findViewById(R.id.favorite_btn)
        favoriteButton.setOnClickListener {
            // Your code to handle favorite benefits action
            val intent = Intent(activity, FavoriteBenefitsActivity::class.java)
            startActivity(intent)
        }

//        // Set the recent benefits button action
//        val recentButton: Button = view.findViewById(R.id.recent)
//        recentButton.setOnClickListener {
//            // Your code to handle recent benefits action
//            val intent = Intent(activity, RecentBenefitsActivity::class.java)
//            startActivity(intent)
//        }

        return view
    }
}
