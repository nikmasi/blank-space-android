package com.example.blankspace.screens.pocetne.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BgCard2() {
    Surface(color = Color(0xFFF0DAE7) // RGB(240, 218, 231)
        , modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.offset(y = (-30).dp)
        ) {
            /*
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.size(10.dp).background(Color.Gray, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier.size(12.dp).background(Color.White, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier.size(10.dp).background(Color.Gray, shape = CircleShape)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                TextButton(onClick = { /* Handle Skip */ }) {
                    Text(text = "Skip", color = Color.White)
                }
                Row {
                    TextButton(onClick = { /* Handle Next */ }) {
                        Text(text = "Next", color = Color.White)
                    }
                }
            }*/
        }
    }
}
