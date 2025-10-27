package com.example.blankspace.screens.autorizacija

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.R
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// Boje za usklađivanje
private val PrimaryDark = Color(0xFF49006B)       // Tamno ljubičasta/Magenta
private val AccentPink = Color(0xFFEC8FB7)       // Roze za naglašavanje/Dugmad
private val CardContainerColor = Color(0xFFF0DAE7) // Svetlo roze (za karticu)

private val CustomTopPadding = 40.dp


@Composable
fun Login(navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2() // Pozadina sa gradijentom

        // Centriranje kartice unutar Box-a
        Login_mainCard(
            navController = navController,
            viewModel = viewModelLogin,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Login_mainCard(navController: NavController, viewModel: LoginViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.75f)
            .shadow(16.dp, RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp)
    ) {
        Spacer(modifier = Modifier.height(19.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Ostavljamo Center da se sve centrira vertikalno
        ) {
            LoginImage()
            LoginHeader()

            // Smanjen razmak
            Spacer(modifier = Modifier.height(12.dp))

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            LoginFields(username = username, password = password, onUsernameChange = { username = it }, onPasswordChange = { password = it })

            // Smanjen razmak između polja i dugmeta
            Spacer(modifier = Modifier.height(12.dp))

            LoginButton(username, password, viewModel, context)

            // Razmak iznad dividera
            Spacer(modifier = Modifier.height(24.dp))

            DividerWithIconModern()

            // Sekcija za navigaciju
            Spacer(modifier = Modifier.height(24.dp))

            // OVDE JE BILO POTREBNO CENTRIRANJE
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ForgotPassword(navController)
                Spacer(modifier = Modifier.height(12.dp))
                SignUpNavigation(navController)
            }
        }
    }
    HandleLoginResponse(uiState = uiState, context = context, navController = navController)
}

// ... (HandleLoginResponse ostaje isto)

@Composable
fun HandleLoginResponse(uiState: UiStateL, context: android.content.Context, navController: NavController) {
    LaunchedEffect(uiState.login?.odgovor) {
        val odgovor = uiState.login?.odgovor
        if (!odgovor.isNullOrEmpty() && odgovor.contains("Pogrešn")) {
            Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uiState.login?.tip) {
        uiState.login?.tip?.let {
            when (it) {
                "S" -> navController.navigate(Destinacije.PocetnaStudent.ruta)
                "B" -> navController.navigate(Destinacije.PocetnaBrucos.ruta)
                "M" -> navController.navigate(Destinacije.PocetnaMaster.ruta)
                "A" -> navController.navigate(Destinacije.PocetnaAdmin.ruta)
            }
        }
    }
}


@Composable
fun LoginHeader() {
    Text(
        text = "Prijava",
        style = MaterialTheme.typography.headlineLarge,
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun LoginImage() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "Logo",
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .padding(vertical = 8.dp),
        contentScale = ContentScale.Fit
    )
}

// **MODIFIKOVANO**: Smanjen razmak između input polja
@Composable
fun LoginFields(username: String, password: String, onUsernameChange: (String) -> Unit, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text("Korisničko ime", color = PrimaryDark) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink,
            focusedTextColor = PrimaryDark,
            unfocusedTextColor = PrimaryDark
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
    // Smanjen razmak između dva input polja
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Lozinka", color = PrimaryDark) },
        visualTransformation = PasswordVisualTransformation(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink,
            focusedTextColor = PrimaryDark,
            unfocusedTextColor = PrimaryDark
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

// ... (LoginButton ostaje isto)
@Composable
fun LoginButton(username: String, password: String, viewModel: LoginViewModel, context: android.content.Context) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if(username.isBlank() || password.isBlank()){
                Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
            }
            else{
                pressed = true
                viewModel.fetchLogin(username,password)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink, // Roza boja
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Prijavi se",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    // Kratak bounce efekat
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}

// ... (DividerWithIconModern ostaje isto)
@Composable
fun DividerWithIconModern() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            color = PrimaryDark.copy(alpha = 0.3f),
            modifier = Modifier.weight(1f),
            thickness = 1.dp
        )
        Text(
            text = "ILI",
            color = PrimaryDark.copy(alpha = 0.6f),
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(
            color = PrimaryDark.copy(alpha = 0.3f),
            modifier = Modifier.weight(1f),
            thickness = 1.dp
        )
    }
}


// ... (ClickableTextStyled ostaje isto)
@Composable
fun ClickableTextStyled(text: String, navController: NavController, destination: String) {
    val color by animateColorAsState(
        targetValue = PrimaryDark,
        label = "clickable_text_color"
    )

    Text(
        text = text,
        color = color,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        modifier = Modifier
            .clickable { navController.navigate(destination) }
    )
}

// **MODIFIKOVANO**: Centriranje lozinke
@Composable
fun ForgotPassword(navController: NavController) {
    // Uklonjen Row i zamena za Text unutar Column koja je centrirana u Login_mainCard
    ClickableTextStyled(
        text = "Zaboravili ste lozinku?",
        navController = navController,
        destination = Destinacije.ZaboravljenaLozinka.ruta
    )
}

// **MODIFIKOVANO**: Centriranje Registracije i Gosta
@Composable
fun SignUpNavigation(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) { // Centriranje unutar Row
            Text(
                "Još nemate nalog? ",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryDark.copy(alpha = 0.8f)
            )

            ClickableTextStyled(
                text = "Registrujte se",
                navController = navController,
                destination = Destinacije.Registracija.ruta
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate(Destinacije.Pocetna.ruta) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = PrimaryDark
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Nastavi kao gost",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}