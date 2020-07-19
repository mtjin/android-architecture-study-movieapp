package com.mtjin.androidarchitecturestudy.di

import android.content.Context
import com.mtjin.androidarchitecturestudy.api.ApiInterface
import com.mtjin.androidarchitecturestudy.data.login.source.local.LoginLocalDataSourceImpl
import com.mtjin.androidarchitecturestudy.data.search.source.local.MovieLocalDataSourceImpl
import com.mtjin.androidarchitecturestudy.data.search.source.remote.MovieRemoteDataSourceImpl
import com.mtjin.androidarchitecturestudy.di.data.RepositoryModule
import com.mtjin.androidarchitecturestudy.di.data.local.LocalDataModule
import com.mtjin.androidarchitecturestudy.di.data.remote.RemoteDataModule
import com.mtjin.androidarchitecturestudy.di.ui.ViewModelFactoryModule
import com.mtjin.androidarchitecturestudy.di.ui.ViewModelModule
import com.mtjin.androidarchitecturestudy.ui.login.LoginComponent
import com.mtjin.androidarchitecturestudy.ui.search.MovieSearchComponent
import com.mtjin.androidarchitecturestudy.ui.splash.SplashComponent
import com.mtjin.androidarchitecturestudy.utils.NetworkManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelModule::class, ViewModelFactoryModule::class, AppSubComponentsModule::class, LocalDataModule::class, RepositoryModule::class,
        RemoteDataModule::class, NetworkModule::class,  ApiModule::class]
)
interface AppComponent {
    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun loginComponent(): LoginComponent.Factory

    fun movieSearchComponent(): MovieSearchComponent.Factory

    fun splashComponent(): SplashComponent.Factory

//    fun loginLocalDataSource(): LoginLocalDataSourceImpl
//
//    fun movieLocalDataSource(): MovieLocalDataSourceImpl
//
//    fun movieRemoteDataSource(): MovieRemoteDataSourceImpl
//
//    fun networkManager(): NetworkManager
//
//    fun apiInterface(): ApiInterface
}