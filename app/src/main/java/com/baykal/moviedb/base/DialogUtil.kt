package com.baykal.moviedb.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ErrorDialog(message: String?) {
    AlertDialog(
        modifier = Modifier
            .width(300.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        shape = RoundedCornerShape(8.dp),
        title = {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                text = "Error"
            )
        },
        text = {
            Text(
                color = Color.Gray,
                fontSize = 14.sp,
                text = message ?: ""
            )
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = { }) {
                Text(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Okay"
                )
            }
        },
        onDismissRequest = { }
    )
}

@Composable
fun LoadingDialog() {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {}
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.White)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Color.Blue
            )
        }
    }
}