package com.mtjin.androidarchitecturestudy.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mtjin.androidarchitecturestudy.R
import com.mtjin.androidarchitecturestudy.di.MyApplication
import com.mtjin.androidarchitecturestudy.ui.login.LoginActivity
import com.mtjin.androidarchitecturestudy.ui.search.MovieSearchActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    //대거
    lateinit var splashComponent: SplashComponent
    lateinit var splashViewModel: SplashViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        //대거
        splashComponent =
            (application as MyApplication).appComponent.splashComponent().create()
        splashComponent.inject(this)
        super.onCreate(savedInstanceState)
        //대거
        splashViewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)

        initViewModelCallback()
        splashViewModel.doSplash()
    }

    private fun initViewModelCallback() {
        with(splashViewModel) {
            goMovieSearch.observe(this@SplashActivity, Observer {
                goMovieSearch()
            })
            goLogin.observe(this@SplashActivity, Observer {
                goLogin()
            })
        }
    }


    private fun goLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun goMovieSearch() {
        showToast(getString(R.string.auto_login_msg))
        startActivity(Intent(this, MovieSearchActivity::class.java))
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
