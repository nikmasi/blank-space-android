package com.example.blankspace.screens.profil_rang_pravila.rang_lista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.*
import com.example.blankspace.ui.theme.RLAccentColor
import com.example.blankspace.viewModels.UiStateRL


@Composable
fun RangListaContent(uiState: UiStateRL) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
    ) {
        itemsIndexed(uiState.rangLista) { index, item ->
            val rankColor = when (index) {
                0 -> Color(0xFFFFD700)
                1 -> Color(0xFFC0C0C0)
                2 -> Color(0xFFA52A2A)
                else -> Color.Transparent
            }

            val textColor = if (index < 3) RLTertiaryColor else Color.Black

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(rankColor.copy(alpha = if (index < 3) 0.3f else 0.0f))
                    .padding(horizontal = 32.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = (index + 1).toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (index < 3) rankColor else Color.DarkGray
                )
                Text(
                    text = item.korisnicko_ime,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                Text(
                    text = item.rang_poeni,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = RLAccentColor
                )
            }
            if (index < uiState.rangLista.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}