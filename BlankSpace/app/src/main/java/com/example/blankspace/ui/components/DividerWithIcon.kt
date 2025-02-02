package com.example.blankspace.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.blankspace.R

@Composable
fun DividerWithIcon() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
        Icon(
            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
            contentDescription = "Icon",
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
        )
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(end = 15.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}