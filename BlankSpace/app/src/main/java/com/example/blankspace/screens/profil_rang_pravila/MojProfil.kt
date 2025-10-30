package com.example.blankspace.screens.profil_rang_pravila


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.screens.NotificationReceiver
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.MojProfilViewModel
import java.util.Calendar
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

val ProfileCardColor = Color.White
val ProfileAccentColor = Color(0xFF49006B)
private val PrimaryDark = Color(0xFF49006B)

@Composable
fun MojProfil(navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        MojProfil_mainCard(navController, viewModelLogin)
    }
}

@Composable
fun MojProfil_mainCard(navController: NavController, viewModelLogin: LoginViewModel) {
    RequestNotificationPermission()
    val viewModel: MojProfilViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiStateLogin.login?.korisnicko_ime?.let { viewModel.fetchMojProfil(it) }
    }

    val profile = uiState.mojprofil
    val profileItems = if (profile != null) {
        listOf(
            "Ime" to profile.ime,
            "Prezime" to profile.prezime,
            "Korisničko ime" to profile.korisnicko_ime,
            "Lični poeni" to profile.licni_poeni.toString(),
            "Rang poeni" to profile.rang_poeni.toString(),
            "Rank" to profile.rank
        )
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(28.dp))

        Spacer(modifier = Modifier.height(44.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(36.dp)),
            colors = CardDefaults.cardColors(containerColor = ProfileCardColor),
            shape = RoundedCornerShape(36.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text="Moj Profil",
                    //text = profile?.korisnicko_ime ?: "Korisnik",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ProfileAccentColor
                )
                Spacer(modifier = Modifier.height(16.dp))

                MojProfilDataList(profileItems)
                Spacer(modifier = Modifier.height(24.dp))
                NotificationTimePicker(viewModelLogin)
            }
        }
    }
}

@Composable
fun MojProfilDataList(profileItems: List<Pair<String, String>>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(profileItems) { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.first,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = item.second,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ProfileAccentColor
                )
            }
            if (index < profileItems.size - 1) {
                Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun NotificationTimePicker(viewModelLogin: LoginViewModel) {
    val context = LocalContext.current
    val calendar = java.util.Calendar.getInstance()

    val initialTime = remember { getNotificationTime(context) }

    val timeState = remember {
        mutableStateOf(
            if (initialTime != null) String.format("%02d:%02d", initialTime.first, initialTime.second)
            else "Nije odabrano"
        )
    }

    val timePickerDialog = android.app.TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            timeState.value = String.format("%02d:%02d", hour, minute)

            saveNotificationTime(context, hour, minute)

            scheduleDailyNotification(context, hour, minute)
            Toast.makeText(context, "Dnevna notifikacija zakazana za ${timeState.value}", Toast.LENGTH_LONG).show()
        },
        calendar.get(java.util.Calendar.HOUR_OF_DAY),
        calendar.get(java.util.Calendar.MINUTE),
        true
    )
    Divider(color = PrimaryDark.copy(alpha = 0.1f))
    Spacer(modifier = Modifier.height(32.dp))


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "🔔 Dnevna Notifikacija",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = ProfileAccentColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { timePickerDialog.show() },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ProfileAccentColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Podesi Vreme:",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Vreme: ",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Text(
                text = timeState.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = ProfileAccentColor
            )
        }
    }
}

fun scheduleDailyNotification(context: Context, hour: Int, minute: Int) {
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        if (before(Calendar.getInstance())) {
            add(Calendar.DAY_OF_MONTH, 1) // ako je prošlo vreme za danas
        }
    }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Notifikacije su omogućene ✅", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Notifikacije su onemogućene ❌", Toast.LENGTH_SHORT).show()
        }
    }

    // Proveri i traži dozvolu samo ako je potreban (API 33+)
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = androidx.core.content.ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionCheck != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}


// Konstante za SharedPreferences
private const val PREFS_NAME = "NotificationPrefs"
private const val KEY_HOUR = "notification_hour"
private const val KEY_MINUTE = "notification_minute"

/**
 * Čuva odabrani sat i minut u SharedPreferences.
 */
fun saveNotificationTime(context: Context, hour: Int, minute: Int) {
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().apply {
        putInt(KEY_HOUR, hour)
        putInt(KEY_MINUTE, minute)
        apply()
    }
}

/**
 * Učitava sačuvani sat i minut iz SharedPreferences.
 * @return Pair<Int, Int>? - Sat i minut, ili null ako nisu sačuvani.
 */
fun getNotificationTime(context: Context): Pair<Int, Int>? {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val hour = prefs.getInt(KEY_HOUR, -1)
    val minute = prefs.getInt(KEY_MINUTE, -1)

    return if (hour != -1 && minute != -1) Pair(hour, minute) else null
}