package com.rxyzent.remainder.core.di.module.ui.fragments.main.birthday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rxyzent.remainder.core.di.annotation.ViewModelKey
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.ui.main.birthday.BirthdayViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BirthdayFragmentModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BirthdayViewModel::class)
    fun bindPhoneVM(vm: BirthdayViewModel): ViewModel

}