package com.rxyzent.remainder.ui.details.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.core.model.db.NigthMode
import com.rxyzent.remainder.databinding.ThemeFragmentBinding
import com.rxyzent.remainder.ui.details.info.InfoViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ThemeFragment:DaggerFragment() {

    private val binding by lazy {
        ThemeFragmentBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory
    private val viewModel: ThemeViewModel by viewModels { vmProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNightMode()
        viewModel.listLiveData.observe(viewLifecycleOwner){
            when(it.mode){
                AppCompatDelegate.MODE_NIGHT_UNSPECIFIED->binding.autoMode.setBackgroundResource(R.drawable.theme_fill_btn_back)
                AppCompatDelegate.MODE_NIGHT_NO-> binding.lightMode.setBackgroundResource(R.drawable.theme_fill_btn_back)
                AppCompatDelegate.MODE_NIGHT_YES->binding.darkMode.setBackgroundResource(R.drawable.theme_fill_btn_back)
            }
        }

        binding.darkMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            viewModel.updateNightMode(NigthMode(AppCompatDelegate.MODE_NIGHT_YES))
            it.setBackgroundResource(R.drawable.theme_fill_btn_back)
            binding.lightMode.setBackgroundResource(R.drawable.theme_btn_back)
            binding.autoMode.setBackgroundResource(R.drawable.theme_btn_back)
        }
        binding.lightMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            viewModel.setNightMode(NigthMode(AppCompatDelegate.MODE_NIGHT_NO))
            it.setBackgroundResource(R.drawable.theme_fill_btn_back)
            binding.autoMode.setBackgroundResource(R.drawable.theme_btn_back)
            binding.darkMode.setBackgroundResource(R.drawable.theme_btn_back)
        }
        binding.autoMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
            viewModel.setNightMode(NigthMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED))
            it.setBackgroundResource(R.drawable.theme_fill_btn_back)
            binding.lightMode.setBackgroundResource(R.drawable.theme_btn_back)
            binding.darkMode.setBackgroundResource(R.drawable.theme_btn_back)
        }

        binding.back.setOnClickListener {
            activity?.finish()
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner){
            activity?.finish()
        }

    }
}