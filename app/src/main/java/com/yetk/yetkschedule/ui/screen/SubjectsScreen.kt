package com.yetk.yetkschedule.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.yetk.yetkschedule.data.local.model.Subject

private val subjectsList = mutableListOf(
    Subject(id = 0, "Subject 1"),
    Subject(id = 1, "Subject 2"),
    Subject(id = 2, "Subject 3"),
    Subject(id = 3, "Subject 4"),
    Subject(id = 4, "Subject 5"),
    Subject(id = 5, "Organisation behavior and HR"),
)

@Composable
fun SubjectsScreen(bottomBarPadding: PaddingValues) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

//    ListItemsScreen(
//        bottomBarPadding = bottomBarPadding,
//        snackbarHostState = snackbarHostState,
//        screenTitle = "Subjects",
//        items = subjectsList,
//        tempListItemPlaceholder = "New subject"
//    ) { subject ->
//        TextFieldListItemWithRevealSwipe(
//            text = subject.name,
//            onBackgroundEndClick = {
//                //TODO hide item
//                //TODO make slide action close if other slide actions is opened
//                scope.launch {
//                    val result = snackbarHostState
//                        .showSnackbar(
//                            message = "Subject removed.",
//                            actionLabel = "Undo",
//                            duration = SnackbarDuration.Short
//                        )
//                    when (result) {
//                        SnackbarResult.ActionPerformed -> {
//                            //TODO show item
//                        }
//
//                        SnackbarResult.Dismissed -> {
//                            //TODO delete item
//                        }
//                    }
//                }
//            }
//        )
//    }
}