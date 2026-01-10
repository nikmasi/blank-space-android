package com.example.blankspace.screens.profil_rang_pravila

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.blankspace.ui.modifiers.horizontalVerticalPadding
import com.example.blankspace.ui.modifiers.mainCardStyle
import com.example.blankspace.ui.modifiers.tableRowStyle
import  com.example.blankspace.ui.theme.*

@Composable
fun PravilaIgre(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        Column(
            modifier = Modifier.horizontalVerticalPadding().padding(top=28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier.mainCardStyle(widthFraction = 1f, heightFraction = 0.9f, cornerRadius = 36.dp)
                    .padding(top=28.dp),
                colors = CardDefaults.cardColors(containerColor = RULES_CARD_BG),
                shape = RoundedCornerShape(36.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.horizontalVerticalPadding(verticalP = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "Pravila Igre",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RULES_TEXT_DARK,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    item { Section1Content() }
                    item { Section2Content() }
                    item { Section3Content() }
                    item { Section4Content() }

                    item {
                        Button(
                            onClick = { navController.navigate(Destinacije.Login.ruta) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .shadow(8.dp, RoundedCornerShape(50)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RULES_ACCENT,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Započni Igru",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Section1Content() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("1. Opis", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = RULES_TEXT_DARK)
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = RULES_ACCENT.copy(alpha = 0.5f))

        Text(
            "Ova igra je stvorena za sve **ljubitelje muzike** koji žele da testiraju svoje znanje o tekstovima pesama različitih izvođača i žanrova, dok se istovremeno takmiče sa svojim prijateljima.",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Vaš zadatak je da **dopunite zadati tekst pesme**, pokazavši koliko dobro poznajete omiljene hitove. Pridružite se zabavi i izazovite svoje prijatelje u ovoj uzbudljivoj muzičkoj avanturi!",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
    }
}

@Composable
fun Section2Content() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("2. Mogućnosti", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = RULES_TEXT_DARK)
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = RULES_ACCENT.copy(alpha = 0.5f))

        BulletPoint("Igraj **sam** ili u **dvoje (duel)**: Izazovite sebe ili se takmičite protiv prijatelja u uzbudljivom duelu.")
        BulletPoint("Odaberi jedan od zadatih **žanrova**: Birajte među raznovrsnim muzičkim žanrovima prema vašim preferencijama.")
        BulletPoint("Odaberi težinu:")

        Column(modifier = Modifier.padding(start = 16.dp, top = 4.dp)) {
            BulletPointSmall("Lakši nivo: Sadrži reči iz **refrena**.")
            BulletPointSmall("Srednji i teži nivoi: Obuhvataju **manje poznate delove** pesama.")
        }
    }
}

@Composable
fun Section3Content() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("3. Tok igre", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = RULES_TEXT_DARK)
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = RULES_ACCENT.copy(alpha = 0.5f))

        Text(
            "Tokom igre, na ekranu se pojavljuje jedan ili više stihova nasumično odabrane pesme, a isti stihovi se i emituju.",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        BulletPoint("Na ekranu se prikazuju prazne linije gde treba upisati **nedostajući tekst**.")
        BulletPoint("Igrači prikupljaju **poene** za svaki tačno popunjen stih.")
        BulletPoint("Na kraju, korisnici se rangiraju na **rang listi** na osnovu osvojenih bodova u duelima.")

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Uronite u svet muzike, testirajte svoje znanje i zabavite se uz ovu dinamičnu igru!",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
    }
}

@Composable
fun Section4Content() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("4. Sakupljaj poene", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = RULES_TEXT_DARK)
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = RULES_ACCENT.copy(alpha = 0.5f))

        Text("Sakupljaj poene i pređi u sledeću **kategoriju**.", color = Color.DarkGray, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TableRowModern("Kategorija", "Brucoš", "Student", "Master", RULES_ACCENT)

            TableRowModern("Nivo", "Niža", "Srednja", "Viša", Color(0xFFF0DAE7))
            TableRowModern("Predlaganje", "-", "Izvođači", "Sve", RULES_CARD_BG)
            TableRowModern("Bonus poeni", "-", "50", "100", Color(0xFFF0DAE7))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Korisnik mora biti **registrovan** da bi se takmičio i skupljao poene!",
            color = RULES_TEXT_DARK,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}


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
fun TableRowModern(col1: String, col2: String, col3: String, col4: String, bgColor: Color) {
    val isHeader = bgColor == RULES_ACCENT
    val textColor = if (isHeader) Color.White else RULES_TEXT_DARK
    val fontWeight = if (isHeader) FontWeight.ExtraBold else FontWeight.Normal
    val fontSize = if (isHeader) 16.sp else 14.sp

    Row(
        modifier = Modifier.tableRowStyle(bgColor,isHeader),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TableCell(
            text = col1,
            weight = 1.5f,
            color = if (isHeader) Color.White else RULES_TEXT_DARK.copy(alpha = 0.8f),
            fontWeight = fontWeight,
            fontSize = fontSize,
            alignment = TextAlign.Start,
            paddingStart = 16.dp
        )
        listOf(col2, col3, col4).forEach { text ->
            TableCell(
                text = text,
                weight = 1f,
                color = textColor,
                fontWeight = if (isHeader) fontWeight else FontWeight.Bold,
                fontSize = fontSize,
                alignment = TextAlign.Center
            )
        }
    }
    if (!isHeader) {
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 0.5.dp)
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