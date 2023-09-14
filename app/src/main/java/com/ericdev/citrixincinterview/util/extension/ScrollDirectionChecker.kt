package com.ericdev.citrixincinterview.util.extension

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*

/** This extension function returns true if the user is scrolling up.
 *
 * Note: The most appropriate use is on a lazy column.
 * */
@Composable
internal fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}