package com.junsu.navigationsample.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(onMoveProfileDetail: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "ProfileScreen")
        Button(onClick = onMoveProfileDetail) { Text(text = "Move Profile Detail Screen") }
    }
}