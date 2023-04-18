package com.segunfrancis.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.segunfrancis.details.databinding.ItemHolidayBinding
import com.segunfrancis.details.model.HolidayHome

class HolidayAdapter : ListAdapter<HolidayHome, HolidayAdapter.HolidayViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        return HolidayViewHolder(
            binding = ItemHolidayBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_holiday, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HolidayViewHolder(private val binding: ItemHolidayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(holiday: HolidayHome) = with(binding) {
            nameValue.text = holiday.name
            localNameValue.text = holiday.name
            date.text = holiday.date
            typesValue.text = if (holiday.public) "Public" else "Not public"
            root.setOnClickListener {  }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<HolidayHome>() {
            override fun areItemsTheSame(oldItem: HolidayHome, newItem: HolidayHome): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: HolidayHome, newItem: HolidayHome): Boolean {
                return false
            }
        }
    }
}
