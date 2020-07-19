package com.mtjin.androidarchitecturestudy.di

import android.content.Context
import com.mtjin.androidarchitecturestudy.utils.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkManager(context: Context): NetworkManager {
        return NetworkManager(context)
    }
}