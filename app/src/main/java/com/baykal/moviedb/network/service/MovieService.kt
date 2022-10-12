package com.baykal.moviedb.network.service

import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.data.response.MovieResult

interface MovieService {
    suspend fun getTrendingMovies(page:Int): MovieResult
    suspend fun getMovieDetail(id: Int): MovieItem
    suspend fun searchMovie(movieName: String): MovieResult
}