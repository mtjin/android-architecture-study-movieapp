package com.mtjin.androidarchitecturestudy.data.search.source.local

import com.mtjin.androidarchitecturestudy.data.search.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MovieLocalDataSource {
    fun insertMovies(movies: List<Movie>): Completable
    fun getAllMovies(): Single<List<Movie>>
    fun getSearchMovies(title: String): Single<List<Movie>>
    fun deleteAllMovies(): Completable
}