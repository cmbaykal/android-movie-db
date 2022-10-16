package com.baykal.moviedb.ui.screen.searchMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.domain.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseViewModel() {

    private val movieList: MutableList<MovieItem> = mutableListOf()
    private val _movies = MutableStateFlow<List<MovieItem>>(listOf())
    val movies: StateFlow<List<MovieItem>> = _movies.asStateFlow()

    fun searchMovie(name: String) {
        searchMovieUseCase.observe(name).collectData { response ->
            response?.let {
                movieList.addAll(it.results)
                _movies.value = movieList.toMutableList()
            }
        }
    }
}