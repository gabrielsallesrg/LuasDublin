package com.gsrg.luasdublin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gsrg.luasdublin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}