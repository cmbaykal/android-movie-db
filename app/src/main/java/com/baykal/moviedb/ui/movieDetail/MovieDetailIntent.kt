package com.baykal.moviedb.ui.movieDetail

sealed class MovieDetailIntent {
    class FetchMovieDetail(val id: Int) : MovieDetailIntent()
}