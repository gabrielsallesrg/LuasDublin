package com.gsrg.luasdublin.ui.fragments.forecast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ForecastListAdapter : RecyclerView.Adapter<ForecastListViewHolder>() {
    // TODO define list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
        return ForecastListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {
        holder.bind("Xpto", "42") //TODO use data from list
    }

    override fun getItemCount(): Int {
        return 7 //TODO return list size
    }
}