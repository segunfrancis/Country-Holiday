package com.segunfrancis.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.countryholiday.databinding.ItemHolidayBinding
import com.project.countryholiday.model.Holiday

class HolidayAdapter : ListAdapter<Holiday, HolidayAdapter.HolidayViewHolder>(DIFF_UTIL) {

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

        fun bind(holiday: Holiday) = with(binding) {
            nameValue.text = holiday.name
            localNameValue.text = holiday.localName
            date.text = holiday.date
            if (holiday.types.isNotEmpty()) {
                typesValue.text = holiday.types.toString().removeSurrounding("[", "]")
            } else {
                typesLabel.isGone = true
                typesValue.isGone = true
            }
            root.setOnClickListener {  }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Holiday>() {
            override fun areItemsTheSame(oldItem: Holiday, newItem: Holiday): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: Holiday, newItem: Holiday): Boolean {
                return false
            }
        }
    }
}
