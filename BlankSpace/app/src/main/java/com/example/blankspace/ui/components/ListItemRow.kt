package com.example.blankspace.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> ListItemRow(
    item: T,
    index: Int,
    textItems: List<Pair<String, String>>,
    backgroundColor1: Color = Color(0xFFF0DAE7),
    backgroundColor2: Color = Color(0xFFADD8E6)
) {
    val backgroundColor = if (index % 2 == 0) backgroundColor1 else backgroundColor2

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        textItems.forEach { pair ->
            Text(
                text = pair.first,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(
                text = pair.second,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}
