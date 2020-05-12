package com.mtjin.androidarchitecturestudy.data.search.source

import com.mtjin.androidarchitecturestudy.data.search.Movie
import io.reactivex.Flowable
import io.reactivex.Single

interface MovieRepository {
    fun getSearchMovies(
        query: String
    ): Flowable<List<Movie>>

    fun getRemoteSearchMovies(
        query: String
    ): Single<List<Movie>>

    fun getRemotePagingMovies(
        query: String,
        start: Int
    ): Single<List<Movie>>
}