package com.example.blankspace.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.blankspace.R

@Composable
fun MyImage(contentScale: ContentScale,num:Int){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground), // ID slike u resursima
        contentDescription = "Blank Space",
        modifier = Modifier.fillMaxWidth().padding(start = (screenWidth/num).dp,end=(screenWidth/num).dp),
        contentScale = contentScale // (Crop, Fit, Inside)
    )
}