package com.baykal.moviedb.ui.screen.movieList

sealed class MovieListIntent {
    object FetchMovieList : MovieListIntent()

    object RefreshList : MovieListIntent()
}