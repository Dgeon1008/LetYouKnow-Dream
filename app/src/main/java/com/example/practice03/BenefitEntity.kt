package com.example.practice03

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "benefits")
data class BenefitEntity(
    @PrimaryKey val title: String,
    val documentId: String,
    val period: String,
    val imageResId: Int,
    val type: String,
    var isFavorite: Boolean = false
)
