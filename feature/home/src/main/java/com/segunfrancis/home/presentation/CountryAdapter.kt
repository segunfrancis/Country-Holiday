package com.segunfrancis.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.segunfrancis.home.R
import com.segunfrancis.home.databinding.ItemCountriesBinding
import com.segunfrancis.home.model.CountryHome
import com.segunfrancis.shared.image_utils.ImageLoadingUtils

class CountryAdapter(
    private val onItemClick: (CountryHome) -> Unit,
    private val imageLoadingUtils: ImageLoadingUtils
) : ListAdapter<CountryHome, CountryAdapter.CountryViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            binding = ItemCountriesBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_countries, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CountryViewHolder(private val binding: ItemCountriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onItemClick.invoke(currentList[adapterPosition]) }
        }

        fun bind(country: CountryHome) {
            imageLoadingUtils.loadImage(binding.countryImage, country.imageUrl)
            binding.countryName.text = country.name
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<CountryHome>() {
            override fun areItemsTheSame(oldItem: CountryHome, newItem: CountryHome): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: CountryHome, newItem: CountryHome): Boolean {
                return false
            }
        }
    }
}
