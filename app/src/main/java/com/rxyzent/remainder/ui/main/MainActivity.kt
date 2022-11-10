package com.rxyzent.remainder.ui.main

import android.os.Bundle
import com.rxyzent.remainder.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}