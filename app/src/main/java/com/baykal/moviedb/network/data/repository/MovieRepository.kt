package com.baykal.moviedb.network.data.repository

import com.baykal.moviedb.base.BaseRepository
import com.baykal.moviedb.network.service.MovieServiceImp
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieServiceImp
) : BaseRepository() {

    fun getTrendingMovie(page: Int) = fetch {
        service.getTrendingMovies(page)
    }

    fun getMovieDetail(id: Int) = fetch {
        service.getMovieDetail(id)
    }

    fun searchMovie(movieName: String) = fetch {
        service.searchMovie(movieName)
    }
}