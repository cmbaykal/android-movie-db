package com.baykal.moviedb.ui.screen.movieDetail

import com.baykal.moviedb.network.data.response.MovieItem

sealed class MovieDetailState {
    object Idle : MovieDetailState()

    object Loading : MovieDetailState()

    class MovieDetail(val detail: MovieItem?) : MovieDetailState()

    class Error(val message: String?) : MovieDetailState()
}