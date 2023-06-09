package com.baykal.moviedb.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel : ViewModel() {
    private val scope = viewModelScope

    protected fun <T> Flow<T>.collectData(
        onSuccess: (T?) -> Unit,
        onError: (String?) -> Unit
    ): Job {
        return onEach {
            onSuccess.invoke(it)
        }.catch { e ->
            onError.invoke(e.message)
        }.launchIn(scope)
    }
}