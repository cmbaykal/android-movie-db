package com.baykal.moviedb.network.domain

import com.baykal.moviedb.base.BaseUseCase
import com.baykal.moviedb.network.data.repository.MovieRepository
import com.baykal.moviedb.network.data.response.MovieItem
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseUseCase<Int, MovieItem>() {

    override fun build(params: Int) = movieRepository.getMovieDetail(params)

}