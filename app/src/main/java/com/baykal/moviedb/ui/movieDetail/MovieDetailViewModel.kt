package com.baykal.moviedb.ui.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.domain.MovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase
) : BaseViewModel() {

    private val _movieDetail = MutableLiveData<MovieItem>()
    val movieDetail: LiveData<MovieItem> = _movieDetail

    fun getMovieDetail(id: Int) {
        movieDetailUseCase.observe(id).collectData { response ->
            response?.let {
                _movieDetail.value = it
            }
        }
    }

}