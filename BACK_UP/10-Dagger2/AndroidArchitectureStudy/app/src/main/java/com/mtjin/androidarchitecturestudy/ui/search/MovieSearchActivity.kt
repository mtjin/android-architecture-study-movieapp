package com.mtjin.androidarchitecturestudy.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mtjin.androidarchitecturestudy.R
import com.mtjin.androidarchitecturestudy.base.BaseActivity
import com.mtjin.androidarchitecturestudy.databinding.ActivityMovieSearchBinding
import com.mtjin.androidarchitecturestudy.di.MyApplication
import javax.inject.Inject


class MovieSearchActivity :
    BaseActivity<ActivityMovieSearchBinding>(R.layout.activity_movie_search) {

    private lateinit var movieAdapter: MovieAdapter

    //대거
    lateinit var movieSearhComponent: MovieSearchComponent
    lateinit var movieSearchViewModel: MovieSearchViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        //대거
        movieSearhComponent =
            (application as MyApplication).appComponent.movieSearchComponent().create()
        movieSearhComponent.inject(this)
        super.onCreate(savedInstanceState)
        //대거
        movieSearchViewModel =
            ViewModelProvider(this, viewModelFactory).get(MovieSearchViewModel::class.java)

        binding.vm = movieSearchViewModel
        initViewModelCallback()
        initAdapter()
    }

    private fun initAdapter() {
        movieAdapter = MovieAdapter { movie ->
            Intent(Intent.ACTION_VIEW, Uri.parse(movie.link)).takeIf {
                it.resolveActivity(packageManager) != null
            }?.run(this::startActivity)
        }
        binding.rvMovies.adapter = movieAdapter
    }

    private fun initViewModelCallback() {
        with(movieSearchViewModel) {
            toastMsg.observe(this@MovieSearchActivity, Observer {
                when (toastMsg.value) {
                    MovieSearchViewModel.MessageSet.LAST_PAGE -> showToast(getString(R.string.last_page_msg))
                    MovieSearchViewModel.MessageSet.EMPTY_QUERY -> showToast(getString(R.string.search_input_query_msg))
                    MovieSearchViewModel.MessageSet.NETWORK_ERROR -> showToast(getString(R.string.network_error_msg))
                    MovieSearchViewModel.MessageSet.SUCCESS -> showToast(getString(R.string.load_movie_success_msg))
                    MovieSearchViewModel.MessageSet.NO_RESULT -> showToast(getString(R.string.no_movie_error_msg))
                }
            })
        }
    }
}
