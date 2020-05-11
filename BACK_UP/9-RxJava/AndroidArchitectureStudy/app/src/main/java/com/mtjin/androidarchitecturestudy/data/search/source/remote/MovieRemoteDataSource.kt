package com.mtjin.androidarchitecturestudy.data.search.source.remote

import com.mtjin.androidarchitecturestudy.data.search.MovieResponse
import io.reactivex.Single

interface MovieRemoteDataSource {
    fun getSearchMovies(
        query: String,
        start: Int = 1
    ): Single<MovieResponse>
}