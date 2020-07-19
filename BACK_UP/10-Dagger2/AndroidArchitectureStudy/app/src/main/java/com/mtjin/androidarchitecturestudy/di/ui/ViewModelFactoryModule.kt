package com.mtjin.androidarchitecturestudy.di.ui

import androidx.lifecycle.ViewModelProvider
import com.mtjin.androidarchitecturestudy.di.ui.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory
}
