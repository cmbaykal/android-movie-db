package com.baykal.moviedb.ui.screen.searchMovie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.baykal.moviedb.base.ErrorDialog
import com.baykal.moviedb.base.LoadingDialog
import com.baykal.moviedb.ui.component.MovieComponent
import kotlinx.coroutines.launch

@Composable
fun SearchMovieScreen(navController: NavController) {
    val viewModel: SearchMovieViewModel = hiltViewModel()
    val screenState = viewModel.state.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    var value by remember { mutableStateOf("") }
    val scrollState = rememberLazyGridState()
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text("Movie Name") },
                maxLines = 1
            )
            Button(
                modifier = Modifier.size(55.dp),
                onClick = {
                    focusManager.clearFocus()
                    coroutineScope.launch {
                        viewModel.userIntent.send(SearchMovieIntent.SearchMovie(value))
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            }
        }
        when (screenState.value) {
            is SearchMovieState.Idle -> {}
            is SearchMovieState.Loading -> LoadingDialog()
            is SearchMovieState.Error -> ErrorDialog((screenState.value as SearchMovieState.Error).message)
            is SearchMovieState.SearchList -> {
                val list = (screenState.value as SearchMovieState.SearchList).list

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
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
    }
}
