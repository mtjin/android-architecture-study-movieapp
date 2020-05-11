package com.mtjin.androidarchitecturestudy.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.androidarchitecturestudy.base.BaseViewModel
import com.mtjin.androidarchitecturestudy.data.search.Movie
import com.mtjin.androidarchitecturestudy.data.search.source.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class MovieSearchViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    private var currentQuery: String = ""
    val query = MutableLiveData<String>()
    private val _movieList = MutableLiveData<ArrayList<Movie>>()
    private val _toastMsg = MutableLiveData<MessageSet>()
    private val _isLoading = MutableLiveData<Boolean>(false)

    val movieList: LiveData<ArrayList<Movie>> get() = _movieList
    val toastMsg: LiveData<MessageSet> get() = _toastMsg
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun requestMovie() {
        currentQuery = query.value.toString().trim()
        Log.d(TAG, currentQuery)
        if (currentQuery.isEmpty()) {
            _toastMsg.value = MessageSet.EMPTY_QUERY
        } else {
            _isLoading.value = true
            compositeDisposable.add(
                movieRepository.getSearchMovies(currentQuery)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onError = {
                            _isLoading.value = false
                            Log.d(TAG, it.toString())
                            when (it) {
                                is HttpException -> _toastMsg.value = MessageSet.NETWORK_ERROR
                                else -> _toastMsg.value = MessageSet.NETWORK_ERROR
                            }
                        },
                        onSuccess = { movies ->
                            _isLoading.value = false
                            if (movies.isEmpty()) {
                                _toastMsg.value = MessageSet.NO_RESULT
                            } else {
                                _movieList.value = movies as ArrayList<Movie>
                                _toastMsg.value = MessageSet.SUCCESS
                            }
                        }
                    )

            )
        }
    }

    fun requestPagingMovie(offset: Int) {
        Log.d(TAG, currentQuery)
        compositeDisposable.add(
            movieRepository.getPagingMovies(currentQuery, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {
                        Log.d(TAG, it.toString())
                        _isLoading.value = false
                        when (it) {
                            is HttpException -> MessageSet.NETWORK_ERROR
                            else -> _toastMsg.value = MessageSet.LAST_PAGE
                        }
                    },
                    onSuccess = { movies ->
                        _isLoading.value = false
                        val pagingMovieList = _movieList.value
                        pagingMovieList?.addAll(movies)
                        _movieList.value = pagingMovieList
                        _toastMsg.value = MessageSet.SUCCESS
                    }
                )
        )
    }

    enum class MessageSet {
        LAST_PAGE,
        EMPTY_QUERY,
        NETWORK_ERROR,
        SUCCESS,
        NO_RESULT
    }

    companion object {
        const val TAG = "MovieSearchTAG"
    }
}