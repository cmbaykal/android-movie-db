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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.baykal.moviedb.ui.component.MovieComponent

@Composable
fun SearchMovieScreen(navController: NavController) {
    val viewModel: SearchMovieViewModel = hiltViewModel()
    val movieList by viewModel.movies.collectAsState()

    var value by remember { mutableStateOf("") }

    val scrollState = rememberLazyGridState()

    with(movieList) {
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
                    onClick = { viewModel.searchMovie(value) }
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
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
