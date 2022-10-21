package com.baykal.moviedb.ui.screen.movieDetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.baykal.moviedb.di.IMG_BASE_URL

@Composable
fun MovieDetailScreen(id: Int?) {
    val viewModel: MovieDetailViewModel = hiltViewModel()
    val detail by viewModel.movieDetail.collectAsState()

    LaunchedEffect(detail) {
        if (detail == null && id != null) {
            viewModel.getMovieDetail(id)
        }
    }

    detail?.let { movie ->
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(IMG_BASE_URL + detail?.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = "image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Text(
                modifier = Modifier.padding(10.dp),
                fontSize = 18.sp,
                text = movie.name ?: movie.title ?: movie.originalTitle ?: ""
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    fontSize = 14.sp,
                    text = buildString { movie.genres?.forEach { append("${it.name} ") } }
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    fontSize = 14.sp,
                    text = movie.releaseDate ?: ""
                )
            }
            Text(
                modifier = Modifier.padding(10.dp),
                fontSize = 16.sp,
                text = movie.overview ?: ""
            )
        }
    }
}