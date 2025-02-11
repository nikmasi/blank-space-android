package com.example.blankspace.screens.autorizacija

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.navigation.NavController
import com.example.blankspace.R
import com.example.blankspace.ui.components.DividerWithIcon
import com.example.blankspace.ui.components.MyButton
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.BodyTextClickable
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Login(navController: NavController,viewModelLogin:LoginViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Login_mainCard(navController,viewModelLogin)
    }
}

@Composable
fun Login_mainCard(navController: NavController,viewModel:LoginViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var context = LocalContext.current

    HandleLoginResponse(uiState = uiState, context = context, navController = navController)

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.85f),
        shape = RoundedCornerShape(40.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), // Povećan padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginImage()
            LoginHeader()

            Spacer(modifier = Modifier.height(2.dp))

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            LoginFields(username = username, password = password, onUsernameChange = { username = it }, onPasswordChange = { password = it })
            Spacer(modifier = Modifier.height(12.dp))
            LoginButton(username,password,viewModel,context)

            Spacer(modifier = Modifier.height(12.dp))
            DividerWithIcon()
            Spacer(modifier = Modifier.height(20.dp))

            ForgotPassword(navController)
            Spacer(modifier = Modifier.height(12.dp))
            SignUpNavigation(navController)
        }
    }
}

@Composable
fun HandleLoginResponse(uiState: UiStateL, context: android.content.Context, navController: NavController) {
    LaunchedEffect(uiState.login?.odgovor) {
        val odgovor = uiState.login?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            if (odgovor.contains("Pogrešn")) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
                return@LaunchedEffect
            }
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
        style = MaterialTheme.typography.headlineSmall,
        color = Color(0xFF6A4C9C),
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun LoginImage(){
    Spacer(modifier = Modifier.height(30.dp))
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "Logo",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = (screenWidth / 8).dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LoginFields(username: String, password: String, onUsernameChange: (String) -> Unit, onPasswordChange: (String) -> Unit) {
    OutlinedTextFieldInput(
        value = username,
        onValueChange = onUsernameChange,
        label = "Korisničko ime"
    )
    Spacer(modifier = Modifier.height(2.dp))
    OutlinedTextFieldInput(
        value = password,
        onValueChange = onPasswordChange,
        label = "Lozinka",
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun LoginButton(username: String, password: String, viewModel: LoginViewModel, context: android.content.Context) {
    MyButton(
        onClick = {
            if(username=="" || password==""){
                Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.fetchLogin(username,password)
            }
        },
        text="Prijavi se"
    )
}

@Composable
fun ForgotPassword(navController: NavController) {
    BodyTextClickable(
        text="Zaboravili ste lozinku?",
        navController=navController,
        destination = Destinacije.ZaboravljenaLozinka.ruta
    )
}

@Composable
fun SignUpNavigation(navController: NavController) {
    Row {
        Text("Još nemate nalog? ", style = MaterialTheme.typography.bodyMedium)

        BodyTextClickable(
            text = "Registrujte se",
            navController = navController,
            destination = Destinacije.Registracija.ruta
        )
    }
    Spacer(modifier = Modifier.height(12.dp))

    BodyTextClickable(
        text = "Nastavi kao gost",
        navController = navController,
        destination = Destinacije.Pocetna.ruta
    )
}