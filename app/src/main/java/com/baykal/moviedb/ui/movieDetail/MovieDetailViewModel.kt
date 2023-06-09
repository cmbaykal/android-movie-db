package com.baykal.moviedb.ui.movieDetail

import androidx.lifecycle.viewModelScope
import com.baykal.moviedb.base.BaseViewModel
import com.baykal.moviedb.network.domain.MovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase
) : BaseViewModel() {

    val userIntent = Channel<MovieDetailIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MovieDetailState>(MovieDetailState.Idle)
    val state: StateFlow<MovieDetailState> = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MovieDetailIntent.FetchMovieDetail -> getMovieDetail(it.id)
                }
            }
        }
    }

    private fun getMovieDetail(id: Int) {
        _state.value = MovieDetailState.Loading
        movieDetailUseCase.observe(id).collectData(
            onError = {
                _state.value = MovieDetailState.Error(it)
            },
            onSuccess = {
                _state.value = MovieDetailState.MovieDetail(it)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        userIntent.close()
    }
}