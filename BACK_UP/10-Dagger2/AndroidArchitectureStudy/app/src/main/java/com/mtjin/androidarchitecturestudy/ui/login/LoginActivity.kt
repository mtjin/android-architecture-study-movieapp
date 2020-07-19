package com.mtjin.androidarchitecturestudy.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mtjin.androidarchitecturestudy.R
import com.mtjin.androidarchitecturestudy.base.BaseActivity
import com.mtjin.androidarchitecturestudy.databinding.ActivityLoginBinding
import com.mtjin.androidarchitecturestudy.di.MyApplication
import com.mtjin.androidarchitecturestudy.ui.search.MovieSearchActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    lateinit var loginViewModel: LoginViewModel

    //대거
    lateinit var loginComponent: LoginComponent

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        //대거
        loginComponent = (application as MyApplication).appComponent.loginComponent().create()
        loginComponent.inject(this)
        super.onCreate(savedInstanceState)
        //대거
        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding.vm = loginViewModel
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(loginViewModel) {
            isIdEmpty.observe(this@LoginActivity, Observer {
                showIdEmptyError()
            })
            isPwEmpty.observe(this@LoginActivity, Observer {
                showPwEmptyError()
            })
            loginErrorMsg.observe(this@LoginActivity, Observer {
                showToast(getString(R.string.id_pw_not_correct_error_msg))
            })
            successLogin.observe(this@LoginActivity, Observer {
                goMovieSearch()
            })
        }
    }

    private fun showIdEmptyError() {
        binding.etId.error = getString(R.string.id_empty_error_msg)
    }

    private fun showPwEmptyError() {
        binding.etPw.error = getString(R.string.pw_empty_error_msg)
    }

    private fun goMovieSearch() {
        showToast(getString(R.string.login_success_msg))
        startActivity(Intent(this, MovieSearchActivity::class.java))
        finish()
    }


}
