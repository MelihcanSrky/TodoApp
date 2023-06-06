package com.melihcan.todoapp.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val MCSFlat = RoundedCornerShape(0.dp)
val MCSRadiusSmall = RoundedCornerShape(8.dp)
val MCSRadiusMedium = RoundedCornerShape(12.dp)
val MCSRadiusLarge = RoundedCornerShape(16.dp)
fun MCSRadiusCustom(radius: Int) = RoundedCornerShape(radius.dp)
val MCSRadiusAll = RoundedCornerShape(1000.dp)