package com.baykal.moviedb.network.service

import com.baykal.moviedb.di.API_KEY
import com.baykal.moviedb.di.BASE_URL
import com.baykal.moviedb.network.data.response.MovieItem
import com.baykal.moviedb.network.data.response.MovieResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class MovieServiceImp(
    private val httpClient: HttpClient
) : MovieService {

    override suspend fun getTrendingMovies(page:Int): MovieResult {
        return httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/3/trending/all/day")
                parameter("api_key", API_KEY)
                parameter("page",page)
            }
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun getMovieDetail(id: Int): MovieItem {
        return httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/3/movie/$id")
                parameter("api_key", API_KEY)
                parameter("language", "en-US")
            }
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun searchMovie(movieName: String): MovieResult {
        return httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                path("/3/search/movie")
                parameter("api_key", API_KEY)
                parameter("include_adult", false)
                parameter("language", "en-US")
                parameter("query", movieName)
            }
            contentType(ContentType.Application.Json)
        }.body()
    }
}