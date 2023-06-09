package com.baykal.moviedb.ui.searchMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.domain.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseViewModel() {

    val userIntent = Channel<SearchMovieIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<SearchMovieState>(SearchMovieState.Idle)
    val state: StateFlow<SearchMovieState> = _state

    private val movieList: MutableList<MovieItem> = mutableListOf()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is SearchMovieIntent.SearchMovie -> searchMovie(it.query)
                }
            }
        }
    }

    private fun searchMovie(name: String) {
        _state.value = SearchMovieState.Loading
        searchMovieUseCase.observe(name).collectData(
            onError = {
                _state.value = SearchMovieState.Error(it)
            },
            onSuccess = { response ->
                response?.let {
                    movieList.addAll(it.results)
                    _state.value = SearchMovieState.SearchList(movieList.toMutableList())
                }
            }
        )
    }
}