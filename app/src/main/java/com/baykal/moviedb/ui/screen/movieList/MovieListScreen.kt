package com.baykal.moviedb.ui.screen.movieList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.baykal.moviedb.base.isScrolledToEnd
import com.baykal.moviedb.base.scrollToEnd
import com.baykal.moviedb.ui.component.MovieComponent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MovieListScreen(navController: NavController) {
    val viewModel: MovieListViewModel = hiltViewModel()
    val movieList by viewModel.movies.collectAsState()

    val scrollState = rememberLazyGridState()
    val swipeRefreshState = rememberSwipeRefreshState(false)

    val scrollEndState by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }

    LaunchedEffect(scrollEndState) {
        if (scrollEndState) {
            viewModel.fetchData()
            scrollState.scrollToEnd()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.refreshData() }) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = scrollState
            ) {
                items(movieList) { movie ->
                    MovieComponent(movie) {
                        navController.navigate("movie/${movie.id}")
                    }
                }
            }
        }
    }
}