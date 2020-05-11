package com.mtjin.androidarchitecturestudy.data.search.source

import com.mtjin.androidarchitecturestudy.data.search.Movie
import io.reactivex.Single

interface MovieRepository {
    fun getSearchMovies(
        query: String
    ): Single<List<Movie>>

    fun getPagingMovies(
        query: String,
        start: Int
    ): Single<List<Movie>>
}