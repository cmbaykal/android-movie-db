package com.baykal.moviedb.ui.screen.searchMovie

import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.domain.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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