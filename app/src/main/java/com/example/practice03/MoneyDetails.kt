package com.example.practice03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice03.databinding.MoneyDetailsBinding

class MoneyDetails : AppCompatActivity() {

    private lateinit var binding: MoneyDetailsBinding
    private lateinit var benefitAdapter: BenefitAdapter
    private lateinit var benefitList: List<Benefit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MoneyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        benefitList = listOf(
            Benefit("국가장학금 Ⅰ유형(학생직접지원형)", "국가장학금 Ⅰ유형(학생직접지원형)", "24.5.21 ~ 24.6.20", R.drawable.benefit_image2, "money"),
            Benefit("국가장학금 Ⅱ유형(대학연계지원형)", "국가장학금 Ⅱ유형(대학연계지원형)", "24.5.21 ~ 24.6.20", R.drawable.benefit_image2, "money"),
            Benefit("청년사회서비스사업단(청년신체건강증진서비스)", "청년사회서비스사업단(청년신체건강증진서비스)", "24.3 ~ 24.12", R.drawable.benefit_image6, "money"),
            Benefit("청년마음건강지원사업", "청년마음건강지원사업", "24.3 ~ 24.12", R.drawable.benefit_image6, "money"),
            Benefit("청년 국가기술자격시험 응시료 지원사업", "청년 국가기술자격시험 응시료 지원사업", "상시지원", R.drawable.benefit_image7, "money"),
            Benefit("대중교통비 환급 지원사업(K패스)", "대중교통비 환급 지원사업(K패스)", "24.5.1 ~ ", R.drawable.benefit_image8, "money"),
            Benefit("보호아동 및 자립준비청년 의료비 지원", "보호아동 및 자립준비청년 의료비 지원", "23.10.1 ~ 24.9.30", R.drawable.benefit_image9, "money"),
            )

        benefitAdapter = BenefitAdapter(benefitList) { benefit ->
            val intent = Intent(this, MoneyDetailActivity::class.java).apply {
                putExtra("benefit", benefit.documentId)
0            }
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
