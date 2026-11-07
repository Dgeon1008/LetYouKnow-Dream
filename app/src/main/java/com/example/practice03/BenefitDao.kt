package com.example.practice03

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BenefitDao {
    @Query("SELECT * FROM benefits")
    fun getAll(): List<BenefitEntity>

    @Insert
    fun insert(benefit: BenefitEntity)

    @Update
    fun update(benefit: BenefitEntity)

    @Query("UPDATE benefits SET isFavorite = :isFavorite WHERE title = :title")
    fun updateFavoriteStatus(title: String, isFavorite: Boolean)
}
