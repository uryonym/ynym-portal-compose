package com.uryonym.ynymportal.ui.extensions

import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.delay

suspend fun FocusRequester.requestFocusImeAware() {
    delay(200)
    requestFocus()
}
