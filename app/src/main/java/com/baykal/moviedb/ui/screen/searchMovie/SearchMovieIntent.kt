package com.baykal.moviedb.ui.screen.searchMovie

sealed class SearchMovieIntent {
    class SearchMovie(val query: String) : SearchMovieIntent()
}