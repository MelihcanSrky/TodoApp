package com.melihcan.todoapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.melihcan.todoapp.R

private val Poppins = FontFamily(
    Font(R.font.poppins_medium, FontWeight.W500),
    Font(R.font.poppins_regular),
    Font(R.font.poppins_semibold, FontWeight.W600)
)

val TodoTypo = Typography(
    headlineLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.02).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )
)