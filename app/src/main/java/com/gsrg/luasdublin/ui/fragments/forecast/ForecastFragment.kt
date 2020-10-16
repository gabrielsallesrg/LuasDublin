package com.gsrg.luasdublin.ui.fragments.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.gsrg.luasdublin.R
import com.gsrg.luasdublin.databinding.FragmentForecastBinding
import com.gsrg.luasdublin.ui.fragments.BaseFragment

class ForecastFragment : BaseFragment() {

    private lateinit var binding: FragmentForecastBinding
    private val adapter = ForecastListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        setListeners()
        setRecyclerView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }

    private fun setListeners() {
        binding.refreshButton.setOnClickListener {
            //TODO
            showMessage(binding.root, "TODO")
        }
    }

    private fun setObservers() {
        //TODO
        binding.updatedAtTextView.text = getString(R.string.updated_at, "25:62") //TODO remove placeholder
    }

    private fun setRecyclerView() {
        binding.recyclerView.let {
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.adapter = adapter
        }
    }
}