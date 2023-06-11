package com.baykal.moviedb.ui.screen.movieList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.baykal.moviedb.base.ErrorDialog
import com.baykal.moviedb.base.LoadingDialog
import com.baykal.moviedb.base.isScrolledToEnd
import com.baykal.moviedb.base.scrollToEnd
import com.baykal.moviedb.ui.component.MovieComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieListScreen(navController: NavController) {
    val viewModel: MovieListViewModel = hiltViewModel()
    val screenState = viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberLazyGridState()
    var refreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        coroutineScope.launch {
            viewModel.userIntent.send(MovieListIntent.RefreshList)
        }
    })

    val scrollEndState by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }

    LaunchedEffect(scrollEndState) {
        if (scrollEndState) {
            viewModel.userIntent.send(MovieListIntent.FetchMovieList)
            scrollState.scrollToEnd()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> coroutineScope.launch {
                    if (screenState.value !is MovieListState.MovieList) {
                        viewModel.userIntent.send(MovieListIntent.FetchMovieList)
                    }
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ) {
        when (screenState.value) {
            is MovieListState.Idle -> {}
            is MovieListState.Loading -> LoadingDialog()
            is MovieListState.Error -> ErrorDialog((screenState.value as MovieListState.Error).message)
            is MovieListState.MovieList -> {
                refreshing = false
                val list = (screenState.value as MovieListState.MovieList).list

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = scrollState
                ) {
                    items(list) { movie ->
                        MovieComponent(movie) {
                            navController.navigate("movie/${movie.id}")
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}