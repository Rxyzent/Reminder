package com.rxyzent.remainder.core.di.module.ui.fragments.details.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rxyzent.remainder.core.di.annotation.ViewModelKey
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.ui.details.theme.ThemeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ThemeFragmentModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ThemeViewModel::class)
    fun bindPhoneVM(vm: ThemeViewModel): ViewModel
}