package com.baykal.moviedb.di

import com.baykal.moviedb.network.service.MovieServiceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor

const val BASE_URL = "api.themoviedb.org"
const val IMG_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val API_KEY = "6a3250b8b666ace2104278cd40e42255"
const val TIME_OUT = 60000L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor
    ) = HttpClient(OkHttp) {
        Charsets {
            register(Charsets.UTF_8)
        }
        engine {
            addInterceptor(loggingInterceptor)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
        }
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(HttpCallValidator) {
            validateResponse {
                val code = it.status.value
                when {
                    code in 300..399 -> throw RedirectResponseException(it, "Redirect Error")
                    code in 400..499 -> throw ClientRequestException(it, "Client Error")
                    code in 500..599 -> throw ServerResponseException(it, "Server Error")
                    code >= 600 -> throw ResponseException(it, "Response Error")
                }
            }

            handleResponseExceptionWithRequest { cause, _ ->
                throw cause
            }
        }
    }

    @Singleton
    @Provides
    fun provideService(httpClient: HttpClient) = MovieServiceImp(httpClient)

    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}