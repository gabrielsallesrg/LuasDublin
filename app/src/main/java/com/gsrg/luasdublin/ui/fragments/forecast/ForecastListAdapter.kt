package com.gsrg.luasdublin.ui.fragments.forecast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsrg.luasdublin.domain.model.TramResponse

class ForecastListAdapter : RecyclerView.Adapter<ForecastListViewHolder>() {

    private var dataList: MutableList<TramResponse> = mutableListOf() //TODO change list type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
        return ForecastListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {
        dataList[position].let {
            holder.bind(destination = it.destination, minutes = it.dueMins)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitData(forecastList: List<TramResponse>) {
        if (forecastList != dataList) {
            dataList = forecastList.toMutableList()
            notifyDataSetChanged()
        }
    }
}