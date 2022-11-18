package com.rxyzent.remainder.core.di.module.ui.fragments.details.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rxyzent.remainder.core.di.annotation.ViewModelKey
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.ui.details.detail.DetailViewModel
import com.rxyzent.remainder.ui.main.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailFragmentModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindPhoneVM(vm: DetailViewModel): ViewModel
}