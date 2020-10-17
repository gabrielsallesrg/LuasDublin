package com.gsrg.luasdublin.ui

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.gsrg.luasdublin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
    }

    fun showLoading() {
        loadingProgressBar.isVisible = true
        loadingProgressBar.animate()
    }

    fun hideLoading() {
        loadingProgressBar.isVisible = false
    }
}