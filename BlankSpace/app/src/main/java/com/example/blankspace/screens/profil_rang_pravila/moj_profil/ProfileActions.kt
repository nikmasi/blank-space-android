package com.example.blankspace.screens.profil_rang_pravila.moj_profil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.blankspace.R

@Composable
fun ProfileActions(
    onClickPravilaIgre: () -> Unit, onClickLogut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row{
            OutlinedButton(
                onClick = onClickPravilaIgre,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, ProfileAccentColor)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text=stringResource(id = R.string.game_rules), color = ProfileAccentColor)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onClickLogut,
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB00020)
                )
            ) {

                Spacer(modifier = Modifier.width(8.dp))
                Text( text=stringResource(id = R.string.logout), color = Color.White)
            }
        }
    }
}