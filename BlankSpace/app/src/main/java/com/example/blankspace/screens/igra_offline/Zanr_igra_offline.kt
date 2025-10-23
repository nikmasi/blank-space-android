package com.example.blankspace.screens.igra_offline


import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.data.room.ZanrEntity
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.igra_sam.ZanrIgreHeader
import com.example.blankspace.viewModels.DatabaseViewModel
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.UiStateZ
import com.example.blankspace.viewModels.ZanrViewModel

@Composable
fun Zanr_igra_offline(navController: NavController,selectedNivo:String,databaseViewModel: DatabaseViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Zanr_igra_offline_mainCard(navController,selectedNivo,databaseViewModel)
    }
}

@Composable
fun Zanr_igra_offline_mainCard(navController: NavController,selectedNivo: String,databaseViewModel: DatabaseViewModel) {
    val selectedNivoList = selectedNivo?.split(",")?.map { it.toString() }
    val context = LocalContext.current

    val zanrovi = databaseViewModel.allZanrovi.collectAsState(initial = emptyList())

    var selectedZanrovi by remember { mutableStateOf(setOf<ZanrEntity>()) }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.6f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(22.dp))
            ZanrIgreHeader()
            Spacer(modifier = Modifier.height(22.dp))



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


            Spacer(modifier = Modifier.height(42.dp))

            SmallButton(
                onClick = {
                    if (selectedZanrovi.isNotEmpty()) {
                        val zanroviIds = selectedZanrovi.joinToString(",") { it.id.toString() }
                        //viewModelIgraSam.postaviListu(emptyList())
                        navController.navigate(Destinacije.Igra_offline.ruta + "/$zanroviIds/$selectedNivoList/0/0")
                    } else {
                        Toast.makeText(context, "Niste izabrali nijedan žanr", Toast.LENGTH_SHORT).show()
                    }
                },
                text = "Dalje",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun ZanroviEntityCheckboxList(
    zanrovi: List<ZanrEntity>,
    selectedZanrovi: Set<ZanrEntity>,
    onZanrSelected: (ZanrEntity, Boolean) -> Unit
) {
    zanrovi.forEach { zanr ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 53.dp).clickable { }
        ) {
            Checkbox(
                checked = selectedZanrovi.contains(zanr),
                onCheckedChange = { isChecked -> onZanrSelected(zanr, isChecked) }
            )
            Text(
                text = zanr.naziv,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp),
                color = Color.Black
            )
        }
    }
}