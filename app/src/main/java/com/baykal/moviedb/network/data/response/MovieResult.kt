package com.baykal.moviedb.network.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: MutableList<MovieItem> = mutableListOf(),
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Long?
)
