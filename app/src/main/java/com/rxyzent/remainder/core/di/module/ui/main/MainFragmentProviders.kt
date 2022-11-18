package com.rxyzent.remainder.core.di.module.ui.main

import com.rxyzent.remainder.core.di.module.ui.fragments.main.birthday.BirthdayFragmentModule
import com.rxyzent.remainder.core.di.module.ui.fragments.main.completed.CompletedFragmentModule
import com.rxyzent.remainder.core.di.module.ui.fragments.main.home.HomeFragmentModule
import com.rxyzent.remainder.core.di.module.ui.fragments.main.payment.PaymentFragmentModule
import com.rxyzent.remainder.core.di.module.ui.fragments.main.todo.TodoFragmentModule
import com.rxyzent.remainder.ui.main.birthday.BirthdayFragment
import com.rxyzent.remainder.ui.main.completed.CompletedFragment
import com.rxyzent.remainder.ui.main.home.HomeFragment
import com.rxyzent.remainder.ui.main.payment.PaymentFragment
import com.rxyzent.remainder.ui.main.toDo.TodoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainFragmentProviders {

    @ContributesAndroidInjector(modules = [
        HomeFragmentModule::class
    ])
    fun provideHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [
        BirthdayFragmentModule::class
    ])
    fun provideBirthdayFragment(): BirthdayFragment

    @ContributesAndroidInjector(modules = [
        CompletedFragmentModule::class
    ])
    fun provideCompletedFragment(): CompletedFragment

    @ContributesAndroidInjector(modules = [
        PaymentFragmentModule::class
    ])
    fun providePaymentFragment(): PaymentFragment

    @ContributesAndroidInjector(modules = [
        TodoFragmentModule::class
    ])
    fun provideTodoFragment(): TodoFragment

}