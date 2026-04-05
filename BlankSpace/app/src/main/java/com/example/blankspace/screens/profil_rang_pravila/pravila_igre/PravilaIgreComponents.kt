package com.example.blankspace.screens.profil_rang_pravila.pravila_igre

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.RULES_ACCENT


@Composable
fun BulletPoint(text: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text("• ", color = RULES_ACCENT, fontWeight = FontWeight.Bold)
        Text(text = text, color = Color.DarkGray, fontSize = 16.sp)
    }
}

@Composable
fun BulletPointSmall(text: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text("- ", color = RULES_ACCENT.copy(alpha = 0.8f), fontWeight = FontWeight.SemiBold)
        Text(text = text, color = Color.Gray, fontSize = 14.sp)
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    alignment: TextAlign,
    paddingStart: Dp = 0.dp
) {
    Text(
        text = text,
        modifier = Modifier.weight(weight).padding(start = paddingStart),
        textAlign = alignment,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}