package com.mtjin.androidarchitecturestudy.di

import com.mtjin.androidarchitecturestudy.ui.login.LoginComponent
import com.mtjin.androidarchitecturestudy.ui.search.MovieSearchComponent
import com.mtjin.androidarchitecturestudy.ui.splash.SplashComponent
import dagger.Module

@Module(subcomponents = [SplashComponent::class, LoginComponent::class, MovieSearchComponent::class])
class AppSubComponentsModule