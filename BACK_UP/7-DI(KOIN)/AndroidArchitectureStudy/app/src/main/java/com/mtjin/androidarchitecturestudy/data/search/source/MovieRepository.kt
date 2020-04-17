package com.mtjin.androidarchitecturestudy.data.search.source

import com.mtjin.androidarchitecturestudy.data.search.Movie

interface MovieRepository {
    fun getSearchMovies(
        query: String,
        success: (List<Movie>) -> Unit,
        fail: (Throwable) -> Unit
    )

    fun getPagingMovies(
        query: String,
        start: Int,
        success: (List<Movie>) -> Unit,
        fail: (Throwable) -> Unit
    )
}