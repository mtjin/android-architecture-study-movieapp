package com.mtjin.androidarchitecturestudy.ui.splash

import dagger.Subcomponent

@Subcomponent
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SplashComponent
    }

    fun inject(splashActivity: SplashActivity)
}