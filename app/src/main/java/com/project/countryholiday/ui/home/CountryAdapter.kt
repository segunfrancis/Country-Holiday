package com.project.countryholiday.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.countryholiday.R
import com.project.countryholiday.databinding.ItemCountriesBinding
import com.project.countryholiday.model.Country

class CountryAdapter(private val onItemClick: (Country) -> Unit) :
    ListAdapter<Country, CountryAdapter.CountryViewHolder>(DIFF_UTIL) {

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

        fun bind(country: Country) {
            binding.countryCode.text = country.code
            binding.countryName.text = country.name
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
                return false
            }
        }
    }
}
