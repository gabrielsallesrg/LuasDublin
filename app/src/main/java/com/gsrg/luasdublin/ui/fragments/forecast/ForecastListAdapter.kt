package com.gsrg.luasdublin.ui.fragments.forecast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ForecastListAdapter : RecyclerView.Adapter<ForecastListViewHolder>() {

    private var dataList: MutableList<Int> = mutableListOf() //TODO change list type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
        return ForecastListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {
        holder.bind("Xpto", "42") //TODO use data from list
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitData(forecastList: List<Int>) {
        if (forecastList != dataList) {
            dataList = forecastList.toMutableList()
            notifyDataSetChanged()
        }
    }
}