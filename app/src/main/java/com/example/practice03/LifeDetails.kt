package com.example.practice03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice03.databinding.LifeDetailsBinding

class LifeDetails : AppCompatActivity() {

    private lateinit var binding: LifeDetailsBinding
    private lateinit var benefitAdapter: BenefitAdapter
    private lateinit var benefitList: List<Benefit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LifeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        benefitList = listOf(
            Benefit("청년월세 한시 특별지원", "청년월세 한시 특별지원", "24.4.12 ~ 25.2.25", R.drawable.benefit_image5, "life"),
            Benefit("2024년 청년 전세임대 1순위 입주자 수시모집", "2024년 청년 전세임대 1순위 입주자 수시모집", "24.1.2 ~ 24.12.31", R.drawable.benefit_image5, "life"),
            Benefit("2024년 자립준비청년(청년 유형) 전세임대 입주자 수시모집", "2024년 자립준비청년(청년 유형) 전세임대 입주자 수시모집", "24.1.2 ~ 24.12.31", R.drawable.benefit_image5, "life"),
        )

        benefitAdapter = BenefitAdapter(benefitList) { benefit ->
            val intent = Intent(this, LifeDetailActivity::class.java).apply {
                putExtra("benefit", benefit.documentId)
            }
            startActivity(intent)
        }

        binding.benefitRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.benefitRecyclerView.adapter = benefitAdapter

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                benefitAdapter.filter.filter(newText)
                return true
            }
        })
    }
}
