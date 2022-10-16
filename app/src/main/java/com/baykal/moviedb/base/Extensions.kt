package com.baykal.moviedb.base

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState

fun ScrollableState.isScrolledToEnd() = try {
    when (this) {
        is LazyListState -> {
            layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        }
        is LazyGridState -> {
            layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        }
        else -> false
    }
} catch (e: Exception) {
    false
}

suspend fun ScrollableState.scrollToEnd() = try {
    when (this) {
        is LazyListState -> {
            scrollToItem(layoutInfo.totalItemsCount - 1)
        }
        is LazyGridState -> {
            scrollToItem(layoutInfo.totalItemsCount - 1)
        }
        else -> Unit
    }
} catch (e: Exception) {
}