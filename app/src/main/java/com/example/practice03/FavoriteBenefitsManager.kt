package com.example.practice03

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object FavoriteBenefitsManager {
    private val favoriteBenefits: MutableList<Benefit> = mutableListOf()

    fun addFavorite(context: Context, benefit: Benefit) {
        if (!favoriteBenefits.contains(benefit)) {
            benefit.isFavorite = true
            favoriteBenefits.add(benefit)
            saveFavorites(context)
        }
    }

    fun removeFavorite(context: Context, benefit: Benefit) {
        benefit.isFavorite = false
        favoriteBenefits.remove(benefit)
        saveFavorites(context)
    }

    fun getFavoriteBenefits(): List<Benefit> {
        return favoriteBenefits.toList()
    }

    private fun saveFavorites(context: Context) {
        val sharedPreferences = context.getSharedPreferences("favorite_benefits", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val favoritesSet = favoriteBenefits.map { it.documentId }.toSet()
        editor.putStringSet("favorites", favoritesSet)
        editor.apply()
    }

    fun loadFavoriteBenefits(context: Context, callback: (List<Benefit>) -> Unit) {
        val sharedPreferences = context.getSharedPreferences("favorite_benefits", Context.MODE_PRIVATE)
        val favoritesSet = sharedPreferences.getStringSet("favorites", emptySet())
        val benefitDao = BenefitDatabase.getDatabase(context).benefitDao()
        CoroutineScope(Dispatchers.IO).launch {
            val benefits = benefitDao.getAll().filter { favoritesSet?.contains(it.documentId) == true }.map {
                Benefit(it.documentId, it.title, it.period, it.imageResId, it.type, it.isFavorite)
            }
            withContext(Dispatchers.Main) {
                favoriteBenefits.clear()
                favoriteBenefits.addAll(benefits)
                callback(favoriteBenefits)
            }
        }
    }
}

