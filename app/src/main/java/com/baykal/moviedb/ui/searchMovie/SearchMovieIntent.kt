package com.baykal.moviedb.ui.searchMovie

sealed class SearchMovieIntent {
    class SearchMovie(val query: String) : SearchMovieIntent()
}