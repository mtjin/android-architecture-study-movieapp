package com.mtjin.androidarchitecturestudy.di.data

import com.mtjin.androidarchitecturestudy.data.login.source.LoginRepository
import com.mtjin.androidarchitecturestudy.data.login.source.LoginRepositoryImpl
import com.mtjin.androidarchitecturestudy.data.login.source.local.LoginLocalDataSource
import com.mtjin.androidarchitecturestudy.data.search.source.MovieRepository
import com.mtjin.androidarchitecturestudy.data.search.source.MovieRepositoryImpl
import com.mtjin.androidarchitecturestudy.data.search.source.local.MovieLocalDataSource
import com.mtjin.androidarchitecturestudy.data.search.source.remote.MovieRemoteDataSource
import com.mtjin.androidarchitecturestudy.utils.NetworkManager
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        networkManager: NetworkManager
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource, networkManager)
    }

    @Provides
    fun provideLoginRepository(loginLocalDataSource: LoginLocalDataSource): LoginRepository {
        return LoginRepositoryImpl(loginLocalDataSource)
    }

}
