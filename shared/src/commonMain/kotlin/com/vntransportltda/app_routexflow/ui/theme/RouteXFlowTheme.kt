package com.vntransportltda.app_routexflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import app_routexflow.shared.generated.resources.Res
import app_routexflow.shared.generated.resources.dm_sans_400
import app_routexflow.shared.generated.resources.dm_sans_500
import app_routexflow.shared.generated.resources.dm_sans_600
import app_routexflow.shared.generated.resources.dm_sans_700
import app_routexflow.shared.generated.resources.space_grotesk_500
import app_routexflow.shared.generated.resources.space_grotesk_600
import app_routexflow.shared.generated.resources.space_grotesk_700
import org.jetbrains.compose.resources.Font

object RxfColors {
    val Ink = Color(0xFF17201D)
    val Muted = Color(0xFF7A8580)
    val Line = Color(0xFFE5EBE6)
    val Green = Color(0xFF2C9A6B)
    val GreenDark = Color(0xFF136541)
    val GreenHover = Color(0xFF22714E)
    val GreenSoft = Color(0xFFEAF7EF)
    val GreenRing = Color(0xFFC8E8D3)
    val GreenFocus = Color(0xFFDFF2E6)
    val Orange = Color(0xFFE58B6E)
    val Error = Color(0xFFC65B4D)
    val InputBorder = Color(0xFFDFE8E1)
    val Label = Color(0xFF506057)
    val Placeholder = Color(0xFF9DA9A1)
    val Surface = Color(0xFFFFFFFF)
}

class RxfFonts(val display: FontFamily, val body: FontFamily)

val LocalRxfFonts = staticCompositionLocalOf<RxfFonts> { error("RouteXFlowTheme ausente") }

private val RxfLightScheme = lightColorScheme(
    primary = RxfColors.Green,
    onPrimary = Color.White,
    background = RxfColors.Surface,
    onBackground = RxfColors.Ink,
    surface = RxfColors.Surface,
    onSurface = RxfColors.Ink,
    outline = RxfColors.InputBorder,
    error = RxfColors.Error,
)

@Composable
fun RouteXFlowTheme(content: @Composable () -> Unit) {
    // Space Grotesk (títulos/marca) e DM Sans (corpo), iguais ao admin
    val fonts = RxfFonts(
        display = FontFamily(
            Font(Res.font.space_grotesk_500, FontWeight.Medium),
            Font(Res.font.space_grotesk_600, FontWeight.SemiBold),
            Font(Res.font.space_grotesk_700, FontWeight.Bold),
        ),
        body = FontFamily(
            Font(Res.font.dm_sans_400, FontWeight.Normal),
            Font(Res.font.dm_sans_500, FontWeight.Medium),
            Font(Res.font.dm_sans_600, FontWeight.SemiBold),
            Font(Res.font.dm_sans_700, FontWeight.Bold),
        ),
    )
    MaterialTheme(colorScheme = RxfLightScheme) {
        CompositionLocalProvider(LocalRxfFonts provides fonts) {
            ProvideTextStyle(TextStyle(fontFamily = fonts.body, color = RxfColors.Ink), content)
        }
    }
}
