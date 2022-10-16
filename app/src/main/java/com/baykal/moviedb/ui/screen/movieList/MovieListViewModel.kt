package com.baykal.moviedb.ui.screen.movieList

import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.domain.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase
) : BaseViewModel() {

    private val movieList: MutableList<MovieItem> = mutableListOf()
    private val _movies = MutableStateFlow<List<MovieItem>>(listOf())
    val movies: StateFlow<List<MovieItem>> = _movies.asStateFlow()

    private var page = 1
    private var totalPages = -1

    init {
        fetchData()
    }

    fun refreshData() {
        movieList.clear()
        page = 1
        fetchData()
    }

    fun fetchData() {
        if (totalPages == -1 || page < totalPages) {
            movieListUseCase.observe(page).collectData { response ->
                response?.let {
                    movieList.addAll(it.results)
                    _movies.value = movieList.toMutableList()
                    totalPages = response.totalPages ?: -1
                    page++
                }
            }
        }
    }
}