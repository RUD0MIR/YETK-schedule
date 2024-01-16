package com.example.lessonsschedulemanagerv2.ui.dragdrop

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.ui.dragdrop.rememberDragDropListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//TODO add animations for more smooth experience
@Composable
fun <T> DragDropList(
    modifier: Modifier = Modifier,
    dataItems: List<T>,
    onMove: (Int, Int) -> Unit,
    listItem: @Composable (dataItem: T) -> Unit

) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)
    var showShadow by remember {
        mutableStateOf(false)
    }

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consumeAllChanges()
                        dragDropListState.onDrag(offset = offset)

                        if (overScrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        dragDropListState
                            .checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let {
                                overScrollJob = scope.launch {
                                    dragDropListState.lazyListState.scrollBy(it)
                                }
                            } ?: kotlin.run { overScrollJob?.cancel() }
                    },
                    onDragStart = { offset ->
                        showShadow = true
                        dragDropListState.onDragStart(offset)
                    },
                    onDragEnd = {
                        dragDropListState.onDragInterrupted()
                        showShadow = false
                                },
                    onDragCancel = {
                        showShadow = false
                        dragDropListState.onDragInterrupted()
                    }
                )
            }
            .fillMaxSize(),
        state = dragDropListState.lazyListState
    ) {
        //TODO: custom item here
        itemsIndexed(dataItems) { index, item ->
            Column(
                modifier = Modifier
                    .composed {
                        val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                            index == dragDropListState.currentIndexOfDraggedItem
                        }
                        Modifier.graphicsLayer {
                            translationY = offsetOrNull ?: 0f
                        }
                    }
                    .shadow(if(showShadow && index == dragDropListState.currentIndexOfDraggedItem) 8.dp else 0.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                listItem(item)
            }
        }
    }
}
