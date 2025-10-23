package com.example.blankspace.screens.pocetne.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.MyImage
import com.example.blankspace.ui.components.SmallButton

@Composable
fun PocetnaMainCard(
    navController: NavController,
    imgSize:Int,
    userName: String?,
    isLoggedIn: Boolean,
    onGameSoloClick: () -> Unit,
    onGameDuelClick: () -> Unit,
    onSuggestArtistClick: (() -> Unit)? = null,
    onSuggestSongClick: (() -> Unit)? = null,
    onSearchAndSuggestClick: (() -> Unit)? = null,

) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.85f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MyImage(ContentScale.Crop, imgSize)

            if (isLoggedIn || userName!=null) {
                HeadlineText("Ulogovani ste kao $userName!")
            } else {
                HeadlineText("Igra dopunjavanja tekstova")
                HeadlineText("pesama")
            }

            Spacer(modifier = Modifier.height(22.dp))

            SmallButton(onClick = onGameSoloClick, text = "Igraj sam", style = MaterialTheme.typography.bodyMedium)
            SmallButton(onClick = onGameDuelClick, text = "Igraj u duelu", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(12.dp))

            onSuggestArtistClick?.let {
                SmallButton(onClick = it, text = "Predloži izvođača", style = MaterialTheme.typography.bodyMedium)
            }

            onSuggestSongClick?.let {
                SmallButton(onClick = it, text = "Predloži pesmu", style = MaterialTheme.typography.bodyMedium)
            }

            onSearchAndSuggestClick?.let {
                SmallButton(onClick = it, text = "Pretraži i predloži", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}