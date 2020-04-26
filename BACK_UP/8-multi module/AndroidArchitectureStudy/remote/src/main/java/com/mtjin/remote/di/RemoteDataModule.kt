package com.mtjin.remote.di

import com.mtjin.data.source.search.remote.MovieRemoteDataSource
import com.mtjin.remote.source.search.MovieRemoteDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module


val remoteDataModule: Module = module {
    single<MovieRemoteDataSource> {
        MovieRemoteDataSourceImpl(
            get()
        )
    }
}