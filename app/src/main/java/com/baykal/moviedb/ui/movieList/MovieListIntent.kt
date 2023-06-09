package com.baykal.moviedb.ui.movieList

sealed class MovieListIntent {
    object FetchMovieList : MovieListIntent()

    object RefreshList : MovieListIntent()
}