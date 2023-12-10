package com.yetk.yetkschedule.other

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.ui.theme.Gray60
import com.yetk.yetkschedule.ui.theme.Gray90

enum class SwipeDirection {
    Left,
    Initial,
    Right,
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableSnackbarHost(hostState: SnackbarHostState) {
    if (hostState.currentSnackbarData == null) { return }
    var size by remember { mutableStateOf(Size.Zero) }
    val swipeableState = rememberSwipeableState(SwipeDirection.Initial)
    val width = remember(size) {
        if (size.width == 0f) {
            1f
        } else {
            size.width
        }
    }
    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                when (swipeableState.currentValue) {
                    SwipeDirection.Right,
                    SwipeDirection.Left -> {
                        hostState.currentSnackbarData?.dismiss()
                    }
                    else -> {
                        return@onDispose
                    }
                }
            }
        }
    }
    val offset = with(LocalDensity.current) {
        swipeableState.offset.value.toDp()
    }
    SnackbarHost(
        hostState,
        snackbar = { snackbarData ->
                Snackbar(
                    modifier = Modifier
                        .padding(10.dp)
                        .offset(x = offset),
                    containerColor = Gray90,
                    contentColor = Color.Black,
                    action = {
                        IconButton(onClick = { snackbarData.performAction() }) {
                            Text(text = snackbarData.visuals.actionLabel.toString(), color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                ) {
                    Text(text = snackbarData.visuals.message, color = Gray60)
                }
        },
        modifier = Modifier
            .onSizeChanged { size = Size(it.width.toFloat(), it.height.toFloat()) }
            .swipeable(
                state = swipeableState,
                anchors = mapOf(
                    -width to SwipeDirection.Left,
                    0f to SwipeDirection.Initial,
                    width to SwipeDirection.Right,
                ),
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    )
}