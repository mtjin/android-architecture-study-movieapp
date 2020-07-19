package com.mtjin.androidarchitecturestudy.data.login.source.local

import com.mtjin.androidarchitecturestudy.utils.PreferenceManager
import javax.inject.Inject

class LoginLocalDataSourceImpl @Inject constructor(private val preferenceManager: PreferenceManager) :
    LoginLocalDataSource {
    override var autoLogin: Boolean
        get() = preferenceManager.autoLogin
        set(value) {
            preferenceManager.autoLogin = value
        }
}