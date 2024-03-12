package com.baykal.moviedb.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.baykal.moviedb.di.IMG_BASE_URL
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieComponent(
    movie: MovieItem,
    onClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            elevation = 10.dp,
            onClick = { onClick.invoke(movie.id ?: 0) }
        ) {
            Column(
                modifier = Modifier.background(colorResource(R.color.softWhite))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(IMG_BASE_URL + movie.posterPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                )
                (movie.name ?: movie.title ?: movie.originalTitle)?.let {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        text = it
                    )
                }
            }
        }
    }
}