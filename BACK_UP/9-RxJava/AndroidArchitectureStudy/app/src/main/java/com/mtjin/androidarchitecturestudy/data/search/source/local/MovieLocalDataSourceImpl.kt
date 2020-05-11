package com.mtjin.androidarchitecturestudy.data.search.source.local

import com.mtjin.androidarchitecturestudy.data.search.Movie
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource {
    override fun insertMovies(movies: List<Movie>): Completable {
        return movieDao
            .insertMovies(movies)
            .subscribeOn(Schedulers.io())
    }

    override fun getAllMovies(): Single<List<Movie>> {
        return movieDao
            .getAllMovies()
            .subscribeOn(Schedulers.io())
            .map {
                it
            }
    }

    override fun getSearchMovies(title: String): Single<List<Movie>> {
        return movieDao.getMoviesByTitle(title)
            .subscribeOn(Schedulers.io())
            .map {
                it
            }
    }

    override fun deleteAllMovies(): Completable {
        return movieDao.deleteAllMovies()
            .subscribeOn(Schedulers.io())
    }
}