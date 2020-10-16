package com.gsrg.luasdublin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gsrg.luasdublin.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showMessage(view: View, message: String, longDuration: Boolean = false) {
        val duration = if (longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(view.context, message, duration).show()
    }
}