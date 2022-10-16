package com.baykal.moviedb.ui.screen.movieDetail

import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.domain.MovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase
) : BaseViewModel() {

    private val _movieDetail = MutableStateFlow<MovieItem?>(null)
    val movieDetail: StateFlow<MovieItem?> = _movieDetail.asStateFlow()

    fun getMovieDetail(id: Int) {
        movieDetailUseCase.observe(id).collectData { response ->
            response?.let {
                _movieDetail.value = it
            }
        }
    }
}