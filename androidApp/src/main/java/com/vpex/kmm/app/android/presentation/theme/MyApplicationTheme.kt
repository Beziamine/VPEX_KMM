package com.vpex.kmm.app.android.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val white = Color(0xFFFFFFFF)
    val black = Color(0xFF000000)
    val gray = Color(0xFFCCCBCB)
    val lightGray = Color(0xFFf6f7f9)
    val blue = Color(0xFF5685df)

    val colors = if (darkTheme) {
        darkColors(
            primary = white,
            primaryVariant = white,
            secondary = black,
            secondaryVariant = black,
            background = gray,
            surface = blue,
            onSurface = black,
            error = gray
        )
    } else {
        lightColors(
            primary = white,
            primaryVariant = black,
            secondary = black,
            secondaryVariant = lightGray,
            background = gray,
            surface = blue,
            onSurface = white,
            error = gray
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colors.secondary.toArgb()
            (view.context as Activity).window.navigationBarColor = colors.secondary.toArgb()
        }
    }

    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        ),
        body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        ),
        caption = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
