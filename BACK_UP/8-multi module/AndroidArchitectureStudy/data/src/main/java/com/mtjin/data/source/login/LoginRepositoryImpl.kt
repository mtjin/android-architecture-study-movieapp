package com.mtjin.data.source.login

import com.mtjin.data.source.login.local.LoginLocalDataSource

internal class LoginRepositoryImpl(private val loginLocalDataSource: LoginLocalDataSource) :
    LoginRepository {

    override var autoLogin: Boolean
        get() = loginLocalDataSource.autoLogin
        set(value) {
            loginLocalDataSource.autoLogin = value
        }
}