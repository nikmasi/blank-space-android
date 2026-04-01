package com.example.blankspace.screens.igraj_u_duelu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.screens.igra_sam.InfoBarColor
import com.example.blankspace.screens.igra_sam.TimeWarningColor
import com.example.blankspace.ui.theme.*

@Composable
fun SetupSpeechRecognition(
    speechRecognizer: SpeechRecognizer, onResults: (String) -> Unit, onListeningChange: (Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() { onListeningChange(false) }
            override fun onError(error: Int) { onListeningChange(false) }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) { onResults(matches[0]) }
                onListeningChange(false)
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }
}




@Composable
fun BottomInfoBarDuel(runda: Int, poeni: Int, count: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.align(Alignment.BottomCenter)
            .background(InfoBarColor, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Runda
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Runda: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(text = "$runda", color = PrimaryDark, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }

            // Vreme
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Vreme: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(
                    text = "$count s",
                    color = if (count >= 50) com.example.blankspace.screens.igra_sam.TimeWarningColor else PrimaryDark,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            // Poeni
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Poeni: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(
                    text = "$poeni",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
                Text(text = " \uD834\uDD1E", color = Color.Black) // Nota
            }
        }
    }
}



@Composable
fun SpeechButton2(
    context: Context,
    speechRecognizer: SpeechRecognizer,
    isListening: Boolean,
    onListeningChange: (Boolean) -> Unit,
    modifier: Modifier
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "Dozvola za mikrofon je potrebna.", Toast.LENGTH_SHORT).show()
        }
    }

    Button(
        onClick = {
            if (androidx.core.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                return@Button
            }

            if (!isListening) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "sr-RS")
                }
                speechRecognizer.startListening(intent)
                onListeningChange(true)
            } else {
                speechRecognizer.stopListening()
                onListeningChange(false)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isListening) TimeWarningColor else AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(52.dp)
    ) {
        Icon(Icons.Default.PlayArrow, contentDescription = if (isListening) "Slušam" else "Govori", modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(if (isListening) "Slušam" else "Govori", fontSize = 14.sp)
    }
}

@Composable
fun ActionButton2(
    onClick: () -> Unit, text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier, containerColor: Color = AccentPink,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(52.dp)
    ) {
        Icon(icon, contentDescription = text, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 14.sp)
    }
}
