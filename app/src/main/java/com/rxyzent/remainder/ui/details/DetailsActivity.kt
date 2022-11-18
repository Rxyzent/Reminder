package com.rxyzent.remainder.ui.details

import android.os.Bundle
import androidx.navigation.Navigation
import com.rxyzent.remainder.R
import com.rxyzent.remainder.databinding.ActivityDetailsBinding
import dagger.android.support.DaggerAppCompatActivity

class DetailsActivity : DaggerAppCompatActivity() {

    private val binding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.details_nav_host_fragment)

        val flag = intent.getStringExtra("flag")

        if(flag == "edit"){
            navController.navigate(R.id.infoFragment)

        }else if(flag == "theme"){
            navController.navigate(R.id.themeFragment)
        }
    }
}