package com.yetk.yetkschedule.ui.dragdrop

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.getVisibleItemInfoFor(absolute: Int): LazyListItemInfo? {
    return this.layoutInfo.visibleItemsInfo.getOrNull(absolute - this.layoutInfo.visibleItemsInfo.first().index)
}

val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size

//fun <T> MutableList<T>.move(from: Int, to: Int) {
//    if (from == to)
//        return
//
//    val element = this.removeAt(from) ?: return
//    this.add(to, element)
//}

fun <T> MutableList<T>.move(from: Int, to: Int): List<T> {
    if (from == to)
        return this

    val element = this.removeAt(from) ?: return this
    this.add(to, element)

    return this
}