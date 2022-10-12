package com.baykal.moviedb.network.domain

import com.baykal.moviedb.base.BaseUseCase
import com.baykal.moviedb.network.data.repository.MovieRepository
import com.baykal.moviedb.network.data.response.MovieResult
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseUseCase<String, MovieResult>() {

    override fun build(params: String) = movieRepository.searchMovie(params)

}