package com.baykal.moviedb.ui.searchMovie

import com.baykal.moviedb.network.data.response.MovieItem

sealed class SearchMovieState {
    object Idle : SearchMovieState()

    object Loading : SearchMovieState()

    class SearchList(val list: List<MovieItem>) : SearchMovieState()

    class Error(val message: String?) : SearchMovieState()
}