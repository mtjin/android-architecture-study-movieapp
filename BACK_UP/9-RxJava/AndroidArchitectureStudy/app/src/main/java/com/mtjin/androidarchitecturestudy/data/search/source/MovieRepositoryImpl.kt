package com.mtjin.androidarchitecturestudy.data.search.source

import android.util.Log
import com.mtjin.androidarchitecturestudy.data.search.Movie
import com.mtjin.androidarchitecturestudy.data.search.source.local.MovieLocalDataSource
import com.mtjin.androidarchitecturestudy.data.search.source.remote.MovieRemoteDataSource
import com.mtjin.androidarchitecturestudy.utils.NetworkManager
import io.reactivex.Flowable
import io.reactivex.Single

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val networkManager: NetworkManager
) : MovieRepository {
    override fun getSearchMovies(query: String): Flowable<List<Movie>> {
        return movieLocalDataSource.getSearchMovies(query)
            .onErrorReturn { listOf() }
            .flatMapPublisher { cachedMovies ->
                if (cachedMovies.isEmpty()) {
                    getRemoteSearchMovies(query)
                        .toFlowable()
                        .onErrorReturn { listOf() }
                } else {
                    val local = Single.just(cachedMovies)
                    val remote = getRemoteSearchMovies(query)
                        .onErrorResumeNext { local }
                    Single.concat(local, remote)
                }
            }
    }

    override fun getRemoteSearchMovies(
        query: String
    ): Single<List<Movie>> {
        return movieRemoteDataSource.getSearchMovies(query)
            .flatMap {
                movieLocalDataSource.insertMovies(it.movies)
                    .andThen(Single.just(it.movies))
            }
    }

    override fun getRemotePagingMovies(
        query: String,
        start: Int
    ): Single<List<Movie>> {
        return if (networkManager.checkNetworkState()) {
            movieRemoteDataSource.getSearchMovies(query, start).flatMap {
                if (it.movies.isEmpty()) {
                    Single.error(IllegalStateException("Last Page"))
                } else {
                    if (start != it.start) {
                        Single.error(IllegalStateException("Last Page"))
                    } else {
                        movieLocalDataSource.insertMovies(it.movies)
                            .andThen(Single.just(it.movies))
                    }
                }
            }
        } else {
            Single.error(IllegalStateException("Network Error"))
        }
    }
}