package com.example.blankspace.screens.profil_rang_pravila.pravila_igre

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.blankspace.screens.pocetne.cards.BgCard2
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import  com.example.blankspace.ui.theme.*

@Composable
fun PravilaIgre(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pravila Igre",
                fontSize = 34.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .fillMaxHeight(0.9f)
                    .shadow(20.dp, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item { RuleSection("1", "Opis", "Saznajte o čemu se radi") { Section1Content() } }
                    item { RuleSection("2", "Mogućnosti", "Načini igranja") { Section2Content() } }
                    item { RuleSection("3", "Tok Igre", "Kako se igra") { Section3Content() } }
                    item { RuleSection("4", "Napredak", "Rangovi i poeni") { Section4Content() } }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .shadow(12.dp, RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = RULES_ACCENT),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("KRENI", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RuleSection(
    emoji: String, title: String, subtitle: String, content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = RULES_ACCENT.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(emoji, fontSize = 24.sp)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = RULES_TEXT_DARK)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.padding(start = 24.dp).drawBehind {
            drawLine(
                color = RULES_ACCENT.copy(alpha = 0.2f),
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = 4f
            )
        }.padding(start = 16.dp)) {
            content()
        }
    }
}

@Composable
fun Section4Content() {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Sakupljaj poene i pređi u sledeću kategoriju.", color = Color.DarkGray)

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                ModernTableRow("RANG", "BRUCOŠ", "STUDENT", "MASTER", isHeader = true)
                ModernTableRow("KATEGORIJA", "Niža", "Srednja", "Viša")
                ModernTableRow("POGODNOSTI", "-", "predlaganje novih izvođača", "predlaganje novih izvođača i pesama itd.")
                ModernTableRow("BROJ POENA", "0", "50", "100")
            }
        }
    }
}

@Composable
fun ModernTableRow(c1: String, c2: String, c3: String, c4: String, isHeader: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(
                if (isHeader) RULES_ACCENT.copy(alpha = 0.1f) else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val style = TextStyle(
            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
            fontSize = if (isHeader) 12.sp else 13.sp,
            color = if (isHeader) RULES_ACCENT else Color.DarkGray
        )
        Text(c1, modifier = Modifier.weight(1.2f), style = style)
        Text(c2, modifier = Modifier.weight(1f), style = style, textAlign = TextAlign.Center)
        Text(c3, modifier = Modifier.weight(1f), style = style, textAlign = TextAlign.Center)
        Text(c4, modifier = Modifier.weight(1f), style = style, textAlign = TextAlign.Center)
    }
}

@Composable
fun Section1Content() {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            "Ova igra je stvorena za sve ljubitelje muzike koji žele da testiraju svoje znanje o tekstovima pesama različitih izvođača i žanrova, dok se istovremeno takmiče sa svojim prijateljima.",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Vaš zadatak je da dopunite zadati tekst pesme, pokazavši koliko dobro poznajete omiljene hitove. Pridružite se zabavi i izazovite svoje prijatelje u ovoj uzbudljivoj muzičkoj avanturi!",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
    }
}

@Composable
fun Section2Content() {
    Column(modifier = Modifier.fillMaxWidth()) {

        BulletPoint("Igrajte sami ili u dvoje: Izazovite sebe ili se takmičite protiv prijatelja u uzbudljivom duelu.")
        BulletPoint("Odaberi jedan od zadatih žanrova: Birajte među raznovrsnim muzičkim žanrovima prema vašim preferencijama.")
        BulletPoint("Odaberi težinu:")

        Column(modifier = Modifier.padding(start = 16.dp, top = 4.dp)) {
            BulletPointSmall("Lakši nivo: Sadrži reči iz refrena.")
            BulletPointSmall("Srednji i teži nivoi: Obuhvataju manje poznate delove pesama.")
        }
    }
}

@Composable
fun Section3Content() {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            "Tokom igre, na ekranu se pojavljuje jedan ili više stihova nasumično odabrane pesme, a isti stihovi se i emituju.",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        BulletPoint("Na ekranu se prikazuju prazne linije gde treba upisati nedostajući tekst.")
        BulletPoint("Igrači prikupljaju poene za svaki tačno popunjen stih.")
        BulletPoint("Na kraju, korisnici se rangiraju na rang listi na osnovu osvojenih bodova u duelima.")

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Uronite u svet muzike, testirajte svoje znanje i zabavite se uz ovu dinamičnu igru!",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
    }
}