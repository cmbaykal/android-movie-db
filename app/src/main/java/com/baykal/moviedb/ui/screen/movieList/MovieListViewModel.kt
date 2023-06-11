package com.baykal.moviedb.ui.screen.movieList

import androidx.lifecycle.viewModelScope
import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.domain.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase
) : BaseViewModel() {

    val userIntent = Channel<MovieListIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MovieListState>(MovieListState.Idle)
    val state: StateFlow<MovieListState> = _state

    private val movieList: MutableList<MovieItem> = mutableListOf()

    private var page = 1
    private var totalPages = -1

    init {
        handleIntent()
    }

    private fun refreshData() {
        movieList.clear()
        page = 1
        fetchData()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    MovieListIntent.FetchMovieList -> fetchData()
                    MovieListIntent.RefreshList -> refreshData()
                }
            }
        }
    }

    private fun fetchData() {
        if (totalPages == -1 || page < totalPages) {
            viewModelScope.launch {
                _state.value = MovieListState.Loading
                delay(400)
                movieListUseCase.observe(page).collectData(
                    onError = {
                        _state.value = MovieListState.Error(it)
                    },
                    onSuccess = { response ->
                        response?.let {
                            movieList.addAll(it.results)
                            _state.value = MovieListState.MovieList(movieList.toMutableList())
                            totalPages = response.totalPages ?: -1
                            page++
                        }
                    }
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        userIntent.close()
    }
}