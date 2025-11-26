package com.junsu.navigationsample.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(onMoveProfileDetail : () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "HomeScreen")
        Button(onClick = onMoveProfileDetail) { Text(text = "Move Profile Detail Screen") }

        LazyColumn {
            items(100) { index ->
                Text(text = "Item #$index")
            }
        }
    }
}