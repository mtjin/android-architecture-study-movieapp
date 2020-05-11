package com.mtjin.androidarchitecturestudy.data.search.source

import android.util.Log
import com.mtjin.androidarchitecturestudy.data.search.Movie
import com.mtjin.androidarchitecturestudy.data.search.source.local.MovieLocalDataSource
import com.mtjin.androidarchitecturestudy.data.search.source.remote.MovieRemoteDataSource
import com.mtjin.androidarchitecturestudy.utils.NetworkManager
import io.reactivex.Single

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val networkManager: NetworkManager
) : MovieRepository {

    override fun getSearchMovies(
        query: String
    ): Single<List<Movie>> {
        if (networkManager.checkNetworkState()) {
            return movieRemoteDataSource.getSearchMovies(query)
                .flatMap {
                    if (it.movies.isEmpty()) {
                        Single.error(IllegalStateException("No Search Result"))
                    } else {
                        movieLocalDataSource.insertMovies(it.movies)
                            .andThen(Single.just(it.movies))
                    }
                }
//            // remote 검색 전 local에서 먼저 검색해서 데이터 전달
//            with(movieLocalDataSource.getSearchMovies(query)) {
//                if (this.isNotEmpty()) {
//                    success(this)
//                }
//            }
//            // remote 에서 검색
//            movieRemoteDataSource.getSearchMovies(
//                query,
//                success = {
//                    // remote 성공시 remote 데이터 전달
//                    movieLocalDataSource.insertMovies(it)
//                    success(it)
//                },
//                fail = {
//                    // remote 실패시 local 에서 검색
//                    with(movieLocalDataSource.getSearchMovies(query)) {
//                        if (this.isEmpty()) {
//                            fail(it)
//                        } else {
//                            success(this)
//                        }
//                    }
//                }
//            )
        } else {
            // local 에서 검색
            return movieLocalDataSource.getSearchMovies(query).flatMap { movies ->
                if (movies.isEmpty()) {
                    Log.d("AAA", "" + movies.size)
                    Single.error(IllegalStateException("No Local Data"))
                } else {
                    Log.d("AAA", "" + movies.size)
                    Single.just(movies)
                }
            }
//            with(movieLocalDataSource.getSearchMovies(query)) {
//                if (this.isEmpty()) {
//                    fail(Throwable("해당 영화는 존재하지 않습니다.\n네트워크를 연결해서 검색해주세요"))
//                } else {
//                    success(this)
//                }
//            }
        }
    }

    override fun getPagingMovies(
        query: String,
        start: Int
    ): Single<List<Movie>> {
        return if (networkManager.checkNetworkState()) {
            movieRemoteDataSource.getSearchMovies(query, start).flatMap {
                if (it.movies.isEmpty()) {
                    Single.error(IllegalStateException("No Search Result"))
                } else {
                    if (start != it.start) {
                        Single.error(IllegalStateException("Last Page"))
                    } else {
                        Single.just(it.movies)
                    }
                }
            }
        } else {
            Single.error(IllegalStateException("No Local Data"))
        }
//        if (networkManager.checkNetworkState()) {
//            movieRemoteDataSource.getSearchMovies(
//                query,
//                start,
//                success = {
//                    movieLocalDataSource.insertMovies(it)
//                    success(it)
//                },
//                fail = {
//                    fail(it)
//                }
//            )
//        } else {
//            fail(Throwable("네트워크가 연결이 되어있지 않습니다."))
//        }
    }
}