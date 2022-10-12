package com.baykal.moviedb.network.domain

import com.baykal.moviedb.base.BaseUseCase
import com.baykal.moviedb.network.data.repository.MovieRepository
import com.baykal.moviedb.network.data.response.MovieResult
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseUseCase<Int, MovieResult>() {

    override fun build(params: Int) = movieRepository.getTrendingMovie(params)

}