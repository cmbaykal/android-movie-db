package com.baykal.moviedb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.baykal.moviedb.ui.screen.movieDetail.MovieDetailScreen
import com.baykal.moviedb.ui.screen.movieList.MovieListScreen
import com.baykal.moviedb.ui.screen.searchMovie.SearchMovieScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: ""
            var toolbarTitle by remember { mutableStateOf("") }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = toolbarTitle)
                        },
                        navigationIcon = getNavigationIcon(currentRoute, navController),
                        actions = {
                            if (currentRoute == "movieList") {
                                IconButton(onClick = { navController.navigate("searchMovie") }) {
                                    Icon(
                                        Icons.Filled.Search,
                                        tint = Color.White,
                                        contentDescription = "Search Icon"
                                    )
                                }
                            }
                        }
                    )
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "movieList",
                    modifier = Modifier.padding(it)
                ) {
                    composable(route = "movieList") {
                        toolbarTitle = "Movie List"
                        MovieListScreen(navController)
                    }
                    composable(
                        route = "movie/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStack ->
                        toolbarTitle = "Movie Detail"
                        MovieDetailScreen(backStack.arguments?.getInt("id", 0))
                    }
                    composable(route = "searchMovie") {
                        toolbarTitle = "Search Movie"
                        SearchMovieScreen(navController)
                    }
                }
            }
        }
    }

    @Composable
    fun getNavigationIcon(route: String, navController: NavController): @Composable (() -> Unit)? {
        return if (route != "movieList") {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back Icon")
                }
            }
        } else {
            null
        }
    }
}
