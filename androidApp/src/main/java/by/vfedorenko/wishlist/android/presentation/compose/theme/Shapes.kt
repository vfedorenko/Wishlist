package com.serv.android.presentation.compose.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class Shapes(
    val xsmall: RoundedCornerShape = RoundedCornerShape(0.dp),
    val small: RoundedCornerShape = RoundedCornerShape(4.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(8.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val xlarge: RoundedCornerShape = RoundedCornerShape(24.dp),
    val xxlarge: RoundedCornerShape = RoundedCornerShape(30.dp),
)
