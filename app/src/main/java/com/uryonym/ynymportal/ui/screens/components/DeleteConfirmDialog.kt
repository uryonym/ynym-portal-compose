package com.uryonym.ynymportal.ui.screens.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmDialog(
    isShow: Boolean,
    onChangeShow: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    if (isShow) {
        AlertDialog(
            onDismissRequest = { onChangeShow(false) },
            text = {
                Text(text = "本当に削除しますか？")
            },
            confirmButton = {
                TextButton(onClick = {
                    onChangeShow(false)
                    onDelete()
                }) {
                    Text(text = "はい")
                }
            },
            dismissButton = {
                TextButton(onClick = { onChangeShow(false) }) {
                    Text(text = "いいえ")
                }
            }
        )
    }
}
