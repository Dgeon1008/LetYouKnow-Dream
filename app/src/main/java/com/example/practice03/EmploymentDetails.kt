package com.example.practice03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice03.databinding.EmploymentDetailsBinding

class EmploymentDetails : AppCompatActivity() {

    private lateinit var binding: EmploymentDetailsBinding
    private lateinit var benefitAdapter: BenefitAdapter
    private lateinit var benefitList: List<Benefit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmploymentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        benefitList = listOf(
            Benefit("청년주택드림청약통장", "청년주택드림청약통장", "상시신청", R.drawable.benefit_image1, "employment"),
            Benefit("햇살론유스", "햇살론유스", "상시신청", R.drawable.benefit_image13, "employment"),
            Benefit("청년도약계좌", "청년도약계좌", "매달 초 신청", R.drawable.benefit_image13, "employment"),
            Benefit("청년전용 버팀목전세자금", "청년전용 버팀목전세자금", "접수기관별 상이", R.drawable.benefit_image1, "employment"),
            Benefit("중소기업 취업자 소득세 감면", "중소기업 취업자 소득세 감면", "상시신청", R.drawable.benefit_image13, "employment"),
            Benefit("주거안정 월세대출", "주거안정 월세대출", "접수기관별 상이", R.drawable.benefit_image1, "employment"),
            Benefit("장병내일준비적금 3대1 매칭지원", "장병내일준비적금 3대1 매칭지원", "접수기관별 상이", R.drawable.benefit_image15, "employment"),
            Benefit("청년내일저축계좌", "청년내일저축계좌", "24.5 ~ ", R.drawable.benefit_image6,"employment"),
            Benefit("일반상환학자금", "일반상환학자금", "24.1.3 ~ 24.5.16", R.drawable.benefit_image2, "employment"),
            Benefit("혜택1", "농촌 출신 대학생 학자금 융자", "24.1.3 ~ 24.6.9", R.drawable.benefit_image16, "employment")
        )

        benefitAdapter = BenefitAdapter(benefitList) { benefit ->
            // 리스트의 항목 이름과 동일한 문서 ID를 파이어스토어에서 찾아 이동
            val intent = Intent(this, EmploymentDetailActivity::class.java).apply {
                putExtra("benefit", benefit.documentId) // 항목 이름을 전달
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



