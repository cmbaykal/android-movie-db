package com.baykal.moviedb.ui.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.domain.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val movieList: MutableList<MovieItem> = mutableListOf()
    private val _movies: MutableLiveData<List<MovieItem>> = MutableLiveData()
    val movies: LiveData<List<MovieItem>> = _movies

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
            _loading.value = true
            movieListUseCase.observe(page).collectData { response ->
                _loading.value = false
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