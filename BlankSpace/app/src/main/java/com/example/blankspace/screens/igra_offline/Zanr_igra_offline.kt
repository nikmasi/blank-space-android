package com.example.blankspace.screens.igra_offline

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.data.room.ZanrEntity
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.igra_sam.ZanrIgreHeaderStyled
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.modifiers.buttonStyle
import com.example.blankspace.ui.modifiers.columnMainStyle
import com.example.blankspace.ui.modifiers.mainCardStyle
import com.example.blankspace.viewModels.DatabaseViewModel
import  com.example.blankspace.ui.theme.*

@Composable
fun Zanr_igra_offline(navController: NavController,selectedNivo:String,databaseViewModel: DatabaseViewModel){
    val zanrovi by databaseViewModel.allZanrovi.collectAsState(initial = null)

    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        if (zanrovi == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AccentPink
            )
        } else {
            Zanr_igra_offline_mainCard(
                zanrovi = zanrovi!!,
                onNext = { zanroviIds ->
                    val nivoi = selectedNivo.replace("[", "").replace("]", "")
                    navController.navigate("${Destinacije.Igra_offline.ruta}/$zanroviIds/$nivoi/0/0")
                },
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Zanr_igra_offline_mainCard(zanrovi: List<ZanrEntity>, onNext: (String) -> Unit, modifier: Modifier = Modifier) {
    var selectedZanrovi by remember { mutableStateOf(setOf<ZanrEntity>()) }
    val context = LocalContext.current

    Surface(
        color = CardContainerColor,
        modifier = modifier.mainCardStyle(widthFraction = 0.9f, heightFraction = 0.75f),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.columnMainStyle(paddingValue = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ZanrIgreHeaderStyled()
                Spacer(modifier = Modifier.height(24.dp))
            }

            Column(
                modifier = Modifier.weight(1f).padding(vertical = 16.dp)
            ) {
                ZanroviEntityCheckboxList(
                    zanrovi = zanrovi,
                    selectedZanrovi = selectedZanrovi,
                    onZanrSelected = { zanr, isChecked ->
                        selectedZanrovi = if (isChecked) {
                            selectedZanrovi + zanr
                        } else {
                            selectedZanrovi - zanr
                        }
                    }
                )
            }

            Button(
                onClick = {
                    if (selectedZanrovi.isNotEmpty()) {
                        val ids = selectedZanrovi.joinToString(",") { it.id.toString() }
                        onNext(ids)
                    } else {
                        Toast.makeText(context, "Niste izabrali nijedan žanr", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentPink,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.buttonStyle()
            ) {
                Text(
                    text = "Dalje",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
fun ZanroviEntityCheckboxList(
    zanrovi: List<ZanrEntity>,
    selectedZanrovi: Set<ZanrEntity>,
    onZanrSelected: (ZanrEntity, Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PrimaryDark.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .background(LightBackground, RoundedCornerShape(12.dp))
            .padding(vertical = 4.dp)
            .heightIn(max = 300.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(zanrovi) { zanr ->
                val isSelected = selectedZanrovi.contains(zanr)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isSelected) AccentPink.copy(alpha = 0.1f) else Color.Transparent)
                        .clickable { onZanrSelected(zanr, !isSelected) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked -> onZanrSelected(zanr, isChecked) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = AccentPink,
                            uncheckedColor = PrimaryDark.copy(alpha = 0.7f),
                            checkmarkColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = zanr.naziv,
                        fontSize = 17.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        color = PrimaryDark
                    )
                }
                Divider(color = PrimaryDark.copy(alpha = 0.08f), thickness = 1.dp)
            }
        }
    }
}