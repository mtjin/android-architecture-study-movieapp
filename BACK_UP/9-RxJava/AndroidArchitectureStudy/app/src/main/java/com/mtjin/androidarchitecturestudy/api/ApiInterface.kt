package com.mtjin.androidarchitecturestudy.api

import com.mtjin.androidarchitecturestudy.data.search.MovieResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    /*
    *  검색하고, 스크롤 더불러오기 함수 나눌려다가 합쳤습니다.
    * */
    @GET("v1/search/movie.json")
    fun getSearchMovie(
        @Query("query") query: String,
        @Query("start") start: Int = 1,
        @Query("display") display: Int = 15
    ): Single<MovieResponse>
}