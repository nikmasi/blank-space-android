package com.example.blankspace.screens.autorizacija

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.R
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.RegistracijaViewModel
import com.example.blankspace.viewModels.UiStateR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private val PrimaryDark = Color(0xFF49006B)       // Tamno ljubičasta/Magenta
private val AccentPink = Color(0xFFEC8FB7)       // Roze za dugmad
private val CardContainerColor = Color(0xFFF0DAE7) // Svetlo roze (za karticu)

@Composable
fun Registracija(navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        Registracija_mainCard(
            navController = navController,
            viewModelLogin = viewModelLogin,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Registracija_mainCard(navController: NavController, viewModelLogin: LoginViewModel, modifier: Modifier) {
    val viewModel: RegistracijaViewModel = hiltViewModel()
    val uiStateRegistracija by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    HandleRegistrationResponse(uiStateRegistracija, context, navController, viewModelLogin)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
            .shadow(16.dp, RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp)
    ) {
        Spacer(modifier = Modifier.height(19.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 24.dp), // Veći padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically), // Razmak između elemenata
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { RegistrationHeader() }

            item {
                var name by remember { mutableStateOf("") }
                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var co_password by remember { mutableStateOf("") }
                var question by remember { mutableStateOf("") }
                var answer by remember { mutableStateOf("") }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RegistrationFields(
                        name = name,
                        username = username,
                        password = password,
                        co_password = co_password,
                        question = question,
                        answer = answer,
                        onValueChange = { field, value ->
                            when (field) {
                                "name" -> name = value
                                "username" -> username = value
                                "password" -> password = value
                                "co_password" -> co_password = value
                                "question" -> question = value
                                "answer" -> answer = value
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    RegistrationButton(name, username, password, co_password, question, answer, context, viewModel)

                    Spacer(modifier = Modifier.height(20.dp))
                    DividerWithIconModernReg()
                    Spacer(modifier = Modifier.height(16.dp))

                    // Navigacija
                    LoginNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun HandleRegistrationResponse(uiStateRegistracija: UiStateR, context: android.content.Context, navController: NavController, viewModelLogin: LoginViewModel) {
    LaunchedEffect(uiStateRegistracija.registration?.odgovor) {
        val odgovor = uiStateRegistracija.registration?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            if (odgovor.contains("Lozinka") ||
                odgovor.contains("Odgovor je duži od 255 karaktera!") ||
                odgovor.contains("Pitanje je duže od 255 karaktera!") ||
                odgovor.contains("Korisnik sa tim imenom već postoji!")
            ) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(uiStateRegistracija.registration?.korisnicko_ime) {
        if (uiStateRegistracija.registration != null && uiStateRegistracija.registration?.odgovor.isNullOrEmpty()) {

            navController.navigate(Destinacije.PocetnaBrucos.ruta)
        }
    }
}

@Composable
fun RegistrationHeader() {
    Text(
        text = "Registracija",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    /*
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "Blank Space Logo",
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(bottom = 16.dp),
        contentScale = ContentScale.Fit
    )
    */
}

@Composable
fun RegistrationFields(
    name: String,
    username: String,
    password: String,
    co_password: String,
    question: String,
    answer: String,
    onValueChange: (String, String) -> Unit
) {
    val fields = remember(name, username, password, co_password, question, answer) {
        listOf(
            Triple("Ime i prezime", name) { value: String -> onValueChange("name", value) },
            Triple("Korisničko ime", username) { value: String -> onValueChange("username", value) },
            Triple("Lozinka", password) { value: String -> onValueChange("password", value) },
            Triple("Ponovo unesite lozinku", co_password) { value: String -> onValueChange("co_password", value) },
            Triple("Pitanje za oporavak lozinke", question) { value: String -> onValueChange("question", value) },
            Triple("Odgovor na pitanje", answer) { value: String -> onValueChange("answer", value) }
        )
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        fields.forEachIndexed { index, field ->
            val isPassword = index == 2 || index == 3

            OutlinedTextField(
                value = field.second,
                onValueChange = field.third,
                label = { Text(field.first, color = PrimaryDark) },
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
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
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}

@Composable
fun RegistrationButton(name: String, username: String, password: String, co_password: String, question: String, answer: String, context: Context, viewModel: RegistracijaViewModel) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if (name.isBlank() || username.isBlank() || password.isBlank() || co_password.isBlank() || question.isBlank() || answer.isBlank()) {
                Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
            } else if (!name.contains(" ")) {
                Toast.makeText(context, "Ime i prezime sa razmakom!", Toast.LENGTH_SHORT).show()
            } else {
                pressed = true
                viewModel.fetchRegistracija(name, username, password, co_password, question, answer)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Registruj se",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}

@Composable
fun DividerWithIconModernReg() {
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

@Composable
fun LoginNavigation(navController: NavController) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            "Imate nalog? ",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryDark.copy(alpha = 0.8f)
        )
        ClickableTextStyledReg(
            text = "Prijavite se",
            navController = navController,
            destination = Destinacije.Login.ruta
        )
    }
}

@Composable
fun ClickableTextStyledReg(text: String, navController: NavController, destination: String) {
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