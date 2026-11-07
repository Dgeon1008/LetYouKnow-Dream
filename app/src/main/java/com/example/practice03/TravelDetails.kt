package com.example.practice03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice03.databinding.TravelDetailsBinding

class TravelDetails : AppCompatActivity() {

    private lateinit var binding: TravelDetailsBinding
    private lateinit var benefitAdapter: BenefitAdapter
    private lateinit var benefitList: List<Benefit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TravelDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        benefitList = listOf(
            Benefit("청년문화예술패스", "청년문화예술패스", "24.3.28 ~ 24.11.30", R.drawable.benefit_image10, "travel"),
            Benefit("국민내일배움카드", "국민내일배움카드", "상시신청", R.drawable.benefit_image11, "travel"),
            Benefit("문화누리카드", "문화누리카드", "상시신청", R.drawable.benefit_image6, "travel"),
            Benefit("경남에서 한 달 여행하기 「김해에 반해」", "경남에서 한 달 여행하기 「김해에 반해」", "24.5.27 ~ 24.6.14", R.drawable.benefit_image3, "travel"),
            Benefit("태어난 김에 음성일주", "태어난 김에 음성일주", "24.5.14 ~ 24.6.10", R.drawable.benefit_image4, "travel"),
            Benefit("단양에서 일주일살기", "단양에서 일주일살기", "매월 10일 ~ 20일", R.drawable.benefit_image12, "travel"),
        )

        benefitAdapter = BenefitAdapter(benefitList) { benefit ->
            val intent = Intent(this, TravelDetailActivity::class.java).apply {
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
