package com.mtjin.androidarchitecturestudy.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.androidarchitecturestudy.base.BaseViewModel
import com.mtjin.androidarchitecturestudy.data.search.Movie
import com.mtjin.androidarchitecturestudy.data.search.source.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
        if (currentQuery.isEmpty()) {
            _toastMsg.value = MessageSet.EMPTY_QUERY
        } else {
            compositeDisposable.add(
                movieRepository.getSearchMovies(currentQuery)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { _isLoading.value = true }
                    .doAfterTerminate { _isLoading.value = false }
                    .subscribe({ movies ->
                        if (movies.isEmpty()) {
                            _toastMsg.value = MessageSet.NO_RESULT
                        } else {
                            _movieList.value = movies as ArrayList<Movie>?
                            _toastMsg.value = MessageSet.SUCCESS
                        }
                    }, {
                        _toastMsg.value = MessageSet.NETWORK_ERROR
                    })
            )
        }
    }

    fun requestPagingMovie(offset: Int) {
        compositeDisposable.add(
            movieRepository.getRemotePagingMovies(currentQuery, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _isLoading.value = true }
                .doAfterTerminate { _isLoading.value = false }
                .subscribe({ movies ->
                    val pagingMovieList = _movieList.value
                    pagingMovieList?.addAll(movies)
                    _movieList.value = pagingMovieList
                    _toastMsg.value = MessageSet.SUCCESS
                }, {
                    when (it.message) {
                        "Network Error" -> _toastMsg.value = MessageSet.NETWORK_ERROR
                        else -> _toastMsg.value = MessageSet.LAST_PAGE
                    }
                })
        )
    }

    enum class MessageSet {
        LAST_PAGE,
        EMPTY_QUERY,
        NETWORK_ERROR,
        SUCCESS,
        NO_RESULT
    }
}