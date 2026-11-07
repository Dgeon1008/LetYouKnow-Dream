package com.example.practice03

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object FavoriteBenefitsManager2 {
    private val calendarFragment: MutableList<Benefit> = mutableListOf()

    fun addFavorite(context: Context, benefit: Benefit) {
        if (!calendarFragment.contains(benefit)) {
            calendarFragment.add(benefit)
            saveFavorites(context)
        }
    }

    fun removeFavorite(context: Context, benefit: Benefit) {
        calendarFragment.remove(benefit)
        saveFavorites(context)
    }

    fun getFavoriteBenefits(): List<Benefit> {
        return calendarFragment.toList()
    }

    private fun saveFavorites(context: Context) {
        val sharedPreferences = context.getSharedPreferences("favorite_benefits", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val favoritesSet = calendarFragment.map { it.title }.toSet()
        editor.putStringSet("favorites", favoritesSet)
        editor.apply()
    }

    fun loadFavoriteBenefits(context: Context, callback: (List<Benefit>) -> Unit) {
        val sharedPreferences = context.getSharedPreferences("favorite_benefits", Context.MODE_PRIVATE)
        val favoritesSet = sharedPreferences.getStringSet("favorites", emptySet())
        val benefitDao = BenefitDatabase.getDatabase(context).benefitDao()
        CoroutineScope(Dispatchers.IO).launch {
            val benefits = benefitDao.getAll().filter { favoritesSet?.contains(it.title) == true }.map {
                Benefit(it.title, it.documentId, it.period, it.imageResId, it.type, it.isFavorite)
            }
            withContext(Dispatchers.Main) {
                calendarFragment.clear()
                calendarFragment.addAll(benefits)
                callback(calendarFragment)
            }
        }
    }
}