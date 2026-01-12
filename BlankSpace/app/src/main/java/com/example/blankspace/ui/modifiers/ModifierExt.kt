package com.example.blankspace.ui.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.mainCardStyle(
    widthFraction: Float = 0.85f,
    heightFraction: Float = 0.6f,
    elevation: Dp = 16.dp,
    cornerRadius: Dp = 24.dp
): Modifier = this
    .fillMaxWidth(widthFraction)
    .fillMaxHeight(heightFraction)
    .shadow(elevation, RoundedCornerShape(cornerRadius))

fun Modifier.cardStyle(
    elevation: Dp = 12.dp,
    cornerRadius: Dp = 36.dp
): Modifier = this
    .fillMaxWidth()
    .shadow(elevation, RoundedCornerShape(cornerRadius))


fun Modifier.columnMainStyle(
    paddingValue: Dp = 32.dp
): Modifier = this
    .fillMaxSize()
    .padding(paddingValue)

fun Modifier.buttonStyle(
    width: Float = 0.9f,
    height: Dp = 52.dp
) = this.fillMaxWidth(width).height(height)


fun Modifier.horizontalVerticalPadding(
    horizontalP: Dp = 24.dp,
    verticalP: Dp = 64.dp
) = this.fillMaxSize()
    .padding(horizontal = horizontalP, vertical = verticalP)

fun Modifier.tableRowStyle(
    bgColor: Color,
    isHeader: Boolean
): Modifier = this
    .fillMaxWidth().background(color = bgColor)
    .padding(vertical = if (isHeader) 12.dp else 10.dp)