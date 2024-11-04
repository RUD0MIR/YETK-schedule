package com.yetk.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

enum class SwipeDirection {
    Left,
    Initial,
    Right
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableSnackbarHost(hostState: SnackbarHostState) {
    if (hostState.currentSnackbarData == null) {
        return
    }
    var size by remember { mutableStateOf(Size.Zero) }
    val swipeableState = rememberSwipeableState(SwipeDirection.Initial)
    val width =
        remember(size) {
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
                    SwipeDirection.Left
                    -> {
                        hostState.currentSnackbarData?.dismiss()
                    }

                    else -> {
                        return@onDispose
                    }
                }
            }
        }
    }
    val offset =
        with(LocalDensity.current) {
            swipeableState.offset.value.toDp()
        }
    SnackbarHost(
        hostState,
        snackbar = { snackbarData ->
            YetkSnackbar(
                modifier =
                Modifier
                    .offset(x = offset)
                    .padding(bottom = 16.dp),
                snackbarData
            )
        },
        modifier =
        Modifier
            .onSizeChanged { size = Size(it.width.toFloat(), it.height.toFloat()) }
            .swipeable(
                state = swipeableState,
                anchors =
                mapOf(
                    -width to SwipeDirection.Left,
                    0f to SwipeDirection.Initial,
                    width to SwipeDirection.Right
                ),
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    )
}

@Composable
fun YetkSnackbar(modifier: Modifier = Modifier, snackbarData: SnackbarData) {
    Snackbar(
        modifier = modifier,
        dismissAction = {
            TextButton(onClick = { snackbarData.performAction() }) {
                Text(
                    text = snackbarData.visuals.actionLabel.toString(),
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    ) {
        Text(text = snackbarData.visuals.message)
    }
}
