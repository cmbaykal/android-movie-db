package com.baykal.moviedb.ui.screen.movieDetail

sealed class MovieDetailIntent {
    class FetchMovieDetail(val id: Int) : MovieDetailIntent()
}