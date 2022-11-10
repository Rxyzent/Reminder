package com.rxyzent.remainder.core.di.module.app

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module()
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun context(application: Application): Context


}