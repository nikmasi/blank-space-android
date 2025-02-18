package com.example.blankspace.screens.autorizacija

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.R
import com.example.blankspace.ui.components.DividerWithIcon
import com.example.blankspace.ui.components.MyButton
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.theme.TopAppBarHeight
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.RegistracijaViewModel
import com.example.blankspace.viewModels.UiStateR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Registracija(navController: NavController,viewModelLogin:LoginViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top= TopAppBarHeight+16.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Registracija_mainCard(navController,viewModelLogin)
    }
}

@Composable
fun Registracija_mainCard(navController: NavController,viewModelLogin:LoginViewModel) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.93f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        val viewModel: RegistracijaViewModel = hiltViewModel()
        val uiStateRegistracija by viewModel.uiState.collectAsState()
        var context = LocalContext.current

        HandleRegistrationResponse(uiStateRegistracija,context,navController,viewModelLogin)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start=16.dp, bottom = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RegistrationImage()
            Spacer(modifier = Modifier.height(8.dp))

            HeadlineText( text="Registracija")
            Spacer(modifier = Modifier.height(8.dp))

            var name by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var co_password by remember { mutableStateOf("") }
            var question by remember { mutableStateOf("") }
            var answer by remember { mutableStateOf("") }

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

            Spacer(modifier = Modifier.height(22.dp))
            RegistrationButton(name,username,password,co_password,question,answer,context,viewModel)

            DividerWithIcon()
            LoginNavigation(navController)
        }
    }
}

@Composable
fun HandleRegistrationResponse(uiStateRegistracija: UiStateR, context: android.content.Context, navController: NavController,viewModelLogin: LoginViewModel) {
    LaunchedEffect(uiStateRegistracija.registration?.odgovor) {
        val odgovor = uiStateRegistracija.registration?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            if (odgovor.contains("Lozinka") ||
                odgovor.contains("Odgovor je duži od 255 karaktera!") || odgovor.contains("Pitanje je duže od 255 karaktera!")
                || odgovor.contains("Korisnik sa tim imenom već postoji!")) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
                return@LaunchedEffect
            }
        }
    }

    LaunchedEffect(uiStateRegistracija.registration?.korisnicko_ime) {
        if (uiStateRegistracija.registration != null && uiStateRegistracija.registration?.odgovor.isNullOrEmpty()) {
            viewModelLogin.setKorisnik(uiStateRegistracija)
            navController.navigate(Destinacije.PocetnaBrucos.ruta)
        }
    }
}

@Composable
fun RegistrationImage(){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "Blank Space",
        contentScale = ContentScale.Inside // (Crop, Fit, Inside)
    )
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
    OutlinedTextFieldInput(
        value = name,
        onValueChange = { onValueChange("name", it) },
        label = "Ime i prezime"
    )

    OutlinedTextFieldInput(
        value = username,
        onValueChange = { onValueChange("username", it) },
        label = "Unesite korisničko ime"
    )

    OutlinedTextFieldInput(
        value = password,
        onValueChange = { onValueChange("password", it) },
        label = "Unesite lozinku",
        visualTransformation = PasswordVisualTransformation()
    )

    OutlinedTextFieldInput(
        value = co_password,
        onValueChange = { onValueChange("co_password", it) },
        label = "Ponovo unesite lozinku",
        visualTransformation = PasswordVisualTransformation()
    )

    OutlinedTextFieldInput(
        value = question,
        onValueChange = { onValueChange("question", it) },
        label = "Unesite pitanje za oporavak lozinke"
    )

    OutlinedTextFieldInput(
        value = answer,
        onValueChange = { onValueChange("answer", it) },
        label = "Unesite odgovor na pitanje"
    )
}

@Composable
fun RegistrationButton(name: String,username: String,password: String,co_password: String,question: String,answer: String,context: Context,viewModel: RegistracijaViewModel){
    MyButton( onClick = {
        if(name=="" || username=="" || password=="" || co_password=="" || question=="" || answer==""){
            Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
        }
        else if (!name.contains(" ")){
            Toast.makeText(context, "Ime i prezime sa razmakom!", Toast.LENGTH_SHORT).show()
        }
        else{
            viewModel.fetchRegistracija(name,username,password,co_password,question,answer)
        }
    }, text="Registruj se")
}

@Composable
fun LoginNavigation(navController: NavController) {
    Row {
        Text("Imate nalog? ", style = MaterialTheme.typography.bodyMedium)
        Text(
            text = "Prijavite se",
            color = Color(0xFF6A4C9C),
            modifier = Modifier.clickable {
                navController.navigate(Destinacije.Login.ruta)
            }
        )
    }
}