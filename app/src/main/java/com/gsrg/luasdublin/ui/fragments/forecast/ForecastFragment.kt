package com.gsrg.luasdublin.ui.fragments.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.gsrg.luasdublin.R
import com.gsrg.luasdublin.databinding.FragmentForecastBinding
import com.gsrg.luasdublin.domain.api.Result
import com.gsrg.luasdublin.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.net.UnknownHostException

@AndroidEntryPoint
class ForecastFragment : BaseFragment() {

    private lateinit var binding: FragmentForecastBinding
    private val viewModel: ForecastViewModel by viewModels()
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
        //TODO viewModel.requestForecastList() once
    }

    private fun setListeners() {
        binding.refreshButton.setOnClickListener {
            viewModel.requestForecastList()
        }
    }

    private fun setObservers() {
        viewModel.forecastListLiveData.observe(viewLifecycleOwner, {
            when (val result = it.getContentIfNotHandled()) {
                is Result.Success -> {
                    hideLoading()
                    adapter.submitData(result.data)
                }
                is Result.Error -> {
                    hideLoading()
                    val errorMessage: String = if (result.exception.cause is UnknownHostException) getString(R.string.connection_error) else result.message
                    showMessage(binding.root, errorMessage)
                }
                is Result.Loading -> {
                    showLoading()
                }
                null -> {
                    if (it.peekContent() is Result.Success) {
                        adapter.submitData((it.peekContent() as Result.Success).data)
                    }
                }
            }
        })
        viewModel.lastUpdateAtLiveData.observe(viewLifecycleOwner, {
            binding.updatedAtTextView.text = getString(R.string.updated_at, it)
        })
    }

    private fun setRecyclerView() {
        binding.recyclerView.let {
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.adapter = adapter
        }
    }
}