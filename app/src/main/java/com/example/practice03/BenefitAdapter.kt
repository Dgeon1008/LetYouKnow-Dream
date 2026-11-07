package com.example.practice03

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class BenefitAdapter(
    private var benefits: List<Benefit>,
    private val onClick: (Benefit) -> Unit
) : RecyclerView.Adapter<BenefitAdapter.BenefitViewHolder>(), Filterable {

    private var filteredBenefits: List<Benefit> = benefits

    class BenefitViewHolder(view: View, val onClick: (Benefit) -> Unit) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.benefitTitle)
        private val periodTextView: TextView = view.findViewById(R.id.benefitPeriod)
        private val imageView: ImageView = view.findViewById(R.id.benefitImage)
        private val ddayTextView: TextView = view.findViewById(R.id.benefitDday)
        private val favoriteButton: ImageButton = view.findViewById(R.id.favoriteButton)
        private var currentBenefit: Benefit? = null

        init {
            view.setOnClickListener {
                currentBenefit?.let {
                    onClick(it)
                }
            }

            favoriteButton.setOnClickListener {
                currentBenefit?.let { benefit ->
                    benefit.isFavorite = !benefit.isFavorite
                    updateFavoriteButton(benefit)
                    updateBenefit(view.context, benefit)
                }
            }
        }

        fun bind(benefit: Benefit) {
            currentBenefit = benefit
            titleTextView.text = benefit.title
            periodTextView.text = benefit.period
            imageView.setImageResource(benefit.imageResId)
            updateFavoriteButton(benefit)

            // 디데이 계산 및 표시
            val dday = benefit.getDday()
            if (dday != null) {
                val ddayString = if (dday == 0) "D-Day" else if (dday == 1) "D-1" else if (dday > 0) "D-$dday" else "D+${-dday}"
                ddayTextView.text = ddayString
                if (dday <= 0) {
                    ddayTextView.setTextColor(itemView.context.getColor(R.color.red)) // 빨간 글씨
                } else {
                    ddayTextView.setTextColor(itemView.context.getColor(R.color.red)) // 검은 글씨
                }
                ddayTextView.visibility = View.VISIBLE
            } else {
                ddayTextView.visibility = View.GONE
            }
        }

        private fun updateFavoriteButton(benefit: Benefit) {
            if (benefit.isFavorite) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_border1)
            } else {
                favoriteButton.setImageResource(R.drawable.ic_favorite_border0)
            }
        }

        private fun updateBenefit(context: Context, benefit: Benefit) {
            if (benefit.isFavorite) {
                FavoriteBenefitsManager.addFavorite(context, benefit)
            } else {
                FavoriteBenefitsManager.removeFavorite(context, benefit)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenefitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_benefit, parent, false)
        return BenefitViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BenefitViewHolder, position: Int) {
        holder.bind(filteredBenefits[position])
    }

    override fun getItemCount(): Int = filteredBenefits.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""
                filteredBenefits = if (query.isEmpty()) {
                    benefits
                } else {
                    benefits.filter {
                        it.title.lowercase(Locale.getDefault()).contains(query)
                    }
                }
                return FilterResults().apply { values = filteredBenefits }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredBenefits = results?.values as List<Benefit>
                notifyDataSetChanged()
            }
        }
    }

    fun updateBenefits(newBenefits: List<Benefit>) {
        benefits = newBenefits
        filteredBenefits = newBenefits
        notifyDataSetChanged()
    }
}
