package com.rxyzent.remainder.core.di.module.ui.details

import com.rxyzent.remainder.core.di.module.ui.fragments.details.detail.DetailFragmentModule
import com.rxyzent.remainder.core.di.module.ui.fragments.details.edit.EditFragmentModule
import com.rxyzent.remainder.core.di.module.ui.fragments.details.info.InfoFragmentModule
import com.rxyzent.remainder.core.di.module.ui.fragments.details.theme.ThemeFragmentModule
import com.rxyzent.remainder.ui.details.detail.DetailFragment
import com.rxyzent.remainder.ui.details.edit.EditFragment
import com.rxyzent.remainder.ui.details.info.InfoFragment
import com.rxyzent.remainder.ui.details.theme.ThemeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface DetailsFragmentProviders {

    @ContributesAndroidInjector(modules = [
        DetailFragmentModule::class
    ])
    fun provideDetailFragment(): DetailFragment

    @ContributesAndroidInjector(modules = [
        InfoFragmentModule::class
    ])
    fun provideInfoFragment(): InfoFragment

    @ContributesAndroidInjector(modules = [
        ThemeFragmentModule::class
    ])
    fun provideThemeFragment(): ThemeFragment

    @ContributesAndroidInjector(modules = [
        EditFragmentModule::class
    ])
    fun provideEditFragment(): EditFragment


}