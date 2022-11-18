package com.rxyzent.remainder.core.di.module.ui.fragments.details.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rxyzent.remainder.core.di.annotation.ViewModelKey
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.ui.details.info.InfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface InfoFragmentModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    fun bindPhoneVM(vm: InfoViewModel): ViewModel
}