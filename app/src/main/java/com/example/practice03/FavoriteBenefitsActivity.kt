package com.example.practice03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class FavoriteBenefitsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var benefitAdapter: BenefitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_benefits)

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.favoriteRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 아이템 클릭 이벤트 처리: 상세 페이지로 이동
        val onItemClick: (Benefit) -> Unit = { benefit ->
            // 해당 혜택의 타입에 따라 상세 페이지로 이동
            val intent = when (benefit.type) {
                "employment" -> Intent(this, EmploymentDetailActivity::class.java)
                "life" -> Intent(this, LifeDetailActivity::class.java)
                "travel" -> Intent(this, TravelDetailActivity::class.java)
                "money" -> Intent(this, MoneyDetailActivity::class.java)
                else -> null
            }
            // 상세 페이지로 전달할 데이터 추가
            intent?.putExtra("benefit", benefit.documentId)
            startActivity(intent)
        }

        // 즐겨찾기한 혜택 로드
        loadFavoriteBenefits(onItemClick)
    }

    private fun loadFavoriteBenefits(onItemClick: (Benefit) -> Unit) {
        val favoriteBenefits = FavoriteBenefitsManager.getFavoriteBenefits()
        benefitAdapter = BenefitAdapter(favoriteBenefits, onItemClick)
        recyclerView.adapter = benefitAdapter
    }
}


