package com.gsrg.luasdublin.ui.fragments.forecast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.luasdublin.database.forecast.Forecast

class ForecastListAdapter : RecyclerView.Adapter<ForecastListViewHolder>() {

    private var dataList: MutableList<Forecast> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
        return ForecastListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {
        dataList[position].let {
            holder.bind(destination = it.destination, minutes = it.dueMinutes)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitData(forecastList: List<Forecast>) {
        if (forecastList != dataList) {
            dataList = forecastList.toMutableList()
            notifyDataSetChanged()
        }
    }
}