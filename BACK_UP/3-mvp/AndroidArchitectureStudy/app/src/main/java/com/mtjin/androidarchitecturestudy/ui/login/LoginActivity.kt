package com.mtjin.androidarchitecturestudy.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mtjin.androidarchitecturestudy.R
import com.mtjin.androidarchitecturestudy.data.login.source.LoginRepository
import com.mtjin.androidarchitecturestudy.data.login.source.LoginRepositoryImpl
import com.mtjin.androidarchitecturestudy.data.login.source.local.LoginLocalDataSource
import com.mtjin.androidarchitecturestudy.data.login.source.local.LoginLocalDataSourceImpl
import com.mtjin.androidarchitecturestudy.ui.search.MovieSearchActivity
import com.mtjin.androidarchitecturestudy.utils.PreferenceManager

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var presenter: LoginContract.Presenter
    private lateinit var etId: EditText
    private lateinit var etPw: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initListener()
        inject()
    }

    private fun inject() {
        val preferenceManager = PreferenceManager(this)
        val loginLocalDataSource: LoginLocalDataSource = LoginLocalDataSourceImpl(preferenceManager)
        val loginRepository: LoginRepository = LoginRepositoryImpl(loginLocalDataSource)
        presenter = LoginPresenter(this, loginRepository)
    }

    private fun initView() {
        etId = findViewById(R.id.et_id)
        etPw = findViewById(R.id.et_pw)
        btnLogin = findViewById(R.id.btn_login)
    }

    private fun initListener() {
        btnLogin.setOnClickListener {
            val id = etId.text.toString().trim()
            val pw = etPw.text.toString().trim()
            presenter.doLogin(id, pw)
        }
    }


    override fun showLoginError() {
        Toast.makeText(this, getString(R.string.id_pw_not_correct_error_msg), Toast.LENGTH_SHORT)
            .show()
    }

    override fun showIdEmptyError() {
        etId.error = getString(R.string.id_empty_error_msg)
    }

    override fun showPwEmptyError() {
        etPw.error = getString(R.string.pw_empty_error_msg)
    }

    override fun goMovieSearch() {
        startActivity(Intent(this, MovieSearchActivity::class.java))
        finish()
    }


}
