package com.gsrg.luasdublin.ui.fragments.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.luasdublin.R

class ForecastListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val destinationTextView: TextView = view.findViewById(R.id.destinationTextView)
    private val minutesTextView: TextView = view.findViewById(R.id.minutesTextView)

    fun bind(destination: String, minutes: String) {
        destinationTextView.text = destination
        minutesTextView.text = itemView.context.getString(R.string.minutes_remaining, minutes)
    }

    companion object {
        fun create(parent: ViewGroup): ForecastListViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item, parent, false)
            return ForecastListViewHolder(view)
        }
    }
}