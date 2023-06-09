package com.baykal.moviedb.ui.movieList

import com.baykal.moviedb.network.data.response.MovieItem

sealed class MovieListState {
    object Idle : MovieListState()

    object Loading : MovieListState()

    class MovieList(val list: List<MovieItem>) : MovieListState()

    class Error(val message: String?) : MovieListState()
}