package com.baykal.moviedb.network.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieItem(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("genre_ids")
    val genreIds: MutableList<Long>? = null,
    @SerialName("genres")
    val genres: MutableList<MovieGenre>? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("vote_count")
    val voteCount: Long? = null,
    @SerialName("video")
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("spoken_languages")
    val spokenLanguages: List<MovieLanguage>? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("imdb_id")
    val imdbID: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<MovieCompanies>? = null,
    @SerialName("production_countries")
    val productionCountries: List<MovieCountries>? = null,
)

@Serializable
data class MovieGenre(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("name")
    val name: String? = null
)

@Serializable
data class MovieLanguage(
    @SerialName("english_name")
    val englishName: String? = null,
    @SerialName("iso_639_1")
    val code: String? = null,
    @SerialName("name")
    val name: String? = null
)

@Serializable
data class MovieCompanies(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("logo_path")
    val logoPath: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("origin_country")
    val originCountry: String? = null
)

@Serializable
data class MovieCountries(
    @SerialName("is_3166_1")
    val code: String? = null,
    @SerialName("name")
    val name: String? = null
)