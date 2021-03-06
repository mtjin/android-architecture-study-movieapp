package com.mtjin.androidarchitecturestudy.di.ui

import androidx.lifecycle.ViewModel
import com.mtjin.androidarchitecturestudy.di.ui.ViewModelKey
import com.mtjin.androidarchitecturestudy.ui.login.LoginViewModel
import com.mtjin.androidarchitecturestudy.ui.search.MovieSearchViewModel
import com.mtjin.androidarchitecturestudy.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchViewModel::class)
    abstract fun bindMovieSearchViewModel(movieSearchViewModel: MovieSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}