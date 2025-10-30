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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.draw.shadow
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
import com.example.blankspace.viewModels.DatabaseViewModel

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color.White

@Composable
fun Zanr_igra_offline(navController: NavController,selectedNivo:String,databaseViewModel: DatabaseViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Zanr_igra_offline_mainCard(navController,selectedNivo,databaseViewModel, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Zanr_igra_offline_mainCard(navController: NavController,selectedNivo: String,databaseViewModel: DatabaseViewModel, modifier: Modifier = Modifier) {
    val selectedNivoList = selectedNivo?.split(",")?.map { it.toString() }
    val context = LocalContext.current
    val zanrovi = databaseViewModel.allZanrovi.collectAsState(initial = emptyList())
    var selectedZanrovi by remember { mutableStateOf(setOf<ZanrEntity>()) }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.75f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
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
                    zanrovi = zanrovi.value,
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
                        val zanroviIds = selectedZanrovi.joinToString(",") { it.id.toString() }
                        navController.navigate(Destinacije.Igra_offline.ruta + "/$zanroviIds/$selectedNivoList/0/0")
                    } else {
                        Toast.makeText(context, "Niste izabrali nijedan žanr", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentPink,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(52.dp)
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