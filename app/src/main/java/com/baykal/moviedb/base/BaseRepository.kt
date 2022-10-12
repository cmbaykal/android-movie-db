package  com.baykal.moviedb.base


import kotlinx.coroutines.flow.flow

open class BaseRepository {

    protected fun <T> fetch(apiCall: suspend () -> T) = flow {
        emit(apiCall())
    }

}