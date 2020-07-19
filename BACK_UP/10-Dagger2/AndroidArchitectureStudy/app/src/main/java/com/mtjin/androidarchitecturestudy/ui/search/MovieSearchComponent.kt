package com.mtjin.androidarchitecturestudy.ui.search

import dagger.Subcomponent


@Subcomponent
interface MovieSearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieSearchComponent
    }

    fun inject(movieSearchActivity: MovieSearchActivity)
}