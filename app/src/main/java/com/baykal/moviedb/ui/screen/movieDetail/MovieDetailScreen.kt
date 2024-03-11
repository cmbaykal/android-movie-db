package com.baykal.moviedb.ui.screen.movieDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(420.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(10.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(IMG_BASE_URL + detail?.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = "image"
            )
            Text(
                modifier = Modifier.padding(10.dp),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = movie.name ?: movie.title ?: movie.originalTitle ?: ""
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    text = buildString { movie.genres?.forEach { append("${it.name} ") } }
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    color = Color.Gray,
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