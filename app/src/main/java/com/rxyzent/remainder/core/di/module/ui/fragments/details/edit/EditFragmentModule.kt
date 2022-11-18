package com.rxyzent.remainder.core.di.module.ui.fragments.details.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rxyzent.remainder.core.di.annotation.ViewModelKey
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.ui.details.edit.EditViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EditFragmentModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(EditViewModel::class)
    fun bindPhoneVM(vm: EditViewModel): ViewModel
}