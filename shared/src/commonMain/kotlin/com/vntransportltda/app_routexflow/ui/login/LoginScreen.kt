package com.vntransportltda.app_routexflow.ui.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vntransportltda.app_routexflow.ui.theme.LocalRxfFonts
import com.vntransportltda.app_routexflow.ui.theme.RouteXFlowTheme
import com.vntransportltda.app_routexflow.ui.theme.RxfColors

/**
 * Login do app: tela única, sem abas. Mesma identidade do admin (cores + Space Grotesk/DM Sans),
 * mas com layout próprio de mobile — cena de rota no topo e "sheet" branco de entrada embaixo.
 */
@Composable
fun LoginScreen(
    onLogin: (email: String, password: String) -> Unit = { _, _ -> },
    onForgotPassword: () -> Unit = {},
    onCreateAccount: () -> Unit = {},
    error: String? = null,
) {
    val fonts = LocalRxfFonts.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RxfColors.GreenSoft)
            .imePadding(),
    ) {
        Column(Modifier.fillMaxSize()) {
            // ---------- cena de marca ----------
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                RouteScene()
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 28.dp, vertical = 24.dp),
                ) {
                    Brand()
                    Spacer(Modifier.height(22.dp))
                    Text(
                        buildAnnotatedString {
                            append("Mais entregas.\n")
                            withStyle(SpanStyle(color = RxfColors.Green)) { append("Menos desvios.") }
                        },
                        fontFamily = fonts.display,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
                        lineHeight = 35.sp,
                        letterSpacing = (-1.6).sp,
                        color = RxfColors.Ink,
                    )
                }
            }

            // ---------- sheet de entrada ----------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(24.dp, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp), clip = false, ambientColor = RxfColors.GreenDark, spotColor = RxfColors.GreenDark)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(RxfColors.Surface)
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .padding(horizontal = 28.dp)
                    .padding(top = 26.dp, bottom = 22.dp),
            ) {
                Text(
                    "ENTRAR",
                    color = Color(0xFF9CA6A0),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.4.sp,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "Bora pra rua?",
                    fontFamily = fonts.display,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    letterSpacing = (-1).sp,
                    color = RxfColors.Ink,
                )
                Spacer(Modifier.height(20.dp))

                RxfTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "E-mail",
                    placeholder = "voce@email.com",
                    keyboardType = KeyboardType.Email,
                )
                Spacer(Modifier.height(14.dp))
                RxfTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Senha",
                    placeholder = "Digite sua senha",
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailing = {
                        Text(
                            if (showPassword) "Ocultar" else "Mostrar",
                            color = RxfColors.Green,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { showPassword = !showPassword }
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                        )
                    },
                )

                Text(
                    "Esqueci minha senha",
                    color = RxfColors.Green,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 12.dp)
                        .clickable(onClick = onForgotPassword),
                )

                if (error != null) {
                    Text(error, color = RxfColors.Error, fontSize = 11.sp, modifier = Modifier.padding(top = 10.dp))
                }

                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(RxfColors.Green)
                        .clickable { onLogin(email.trim(), password) }
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                ) {
                    Text(
                        "Entrar no aplicativo",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.CenterStart),
                    )
                    Text("→", color = Color.White, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterEnd))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text("Primeira vez aqui? ", color = Color(0xFF8A958E), fontSize = 12.sp)
                    Text(
                        "Criar conta",
                        color = RxfColors.Green,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable(onClick = onCreateAccount),
                    )
                }
            }
        }
    }
}

@Composable
private fun Brand() {
    val fonts = LocalRxfFonts.current
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        BrandMark()
        Text(
            buildAnnotatedString {
                append("route")
                withStyle(SpanStyle(color = RxfColors.Green)) { append("X") }
                append("flow")
            },
            fontFamily = fonts.display,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            letterSpacing = (-0.8).sp,
            color = RxfColors.Ink,
        )
    }
}

// Três barras inclinadas (skew -23°) como no logo do admin
@Composable
private fun BrandMark() {
    Canvas(Modifier.size(width = 30.dp, height = 24.dp)) {
        val barW = 6.dp.toPx()
        val gap = 3.dp.toPx()
        val skew = 0.4245f // tan(23°)
        val bars = listOf(12.dp.toPx() to 0.55f, 18.dp.toPx() to 0.78f, 24.dp.toPx() to 1f)
        withTransform({
            translate(left = skew * size.height / 2f, top = 0f)
            transform(androidx.compose.ui.graphics.Matrix().apply { this[1, 0] = -skew })
        }) {
            bars.forEachIndexed { i, (h, alpha) ->
                drawRoundRect(
                    color = RxfColors.Green.copy(alpha = alpha),
                    topLeft = Offset(i * (barW + gap), size.height - h),
                    size = Size(barW, h),
                    cornerRadius = CornerRadius(2.dp.toPx()),
                )
            }
        }
    }
}

/** Anéis decorativos + rota tracejada (verde → laranja) com o chip da rota otimizada. */
@Composable
private fun RouteScene() {
    val fonts = LocalRxfFonts.current
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val w = maxWidth
        val h = maxHeight
        Canvas(Modifier.fillMaxSize()) {
            val ring = Stroke(width = 1.dp.toPx())
            drawCircle(RxfColors.GreenRing, 150.dp.toPx(), Offset(size.width + 30.dp.toPx(), 20.dp.toPx()), style = ring)
            drawCircle(RxfColors.GreenRing, 100.dp.toPx(), Offset(size.width + 30.dp.toPx(), 20.dp.toPx()), style = ring)

            val start = Offset(size.width * 0.12f, size.height * 0.80f)
            val end = Offset(size.width * 0.84f, size.height * 0.60f)
            val path = Path().apply {
                moveTo(start.x, start.y)
                cubicTo(size.width * 0.35f, size.height * 1.0f, size.width * 0.50f, size.height * 0.45f, end.x, end.y)
            }
            drawPath(
                path,
                color = RxfColors.Green.copy(alpha = 0.7f),
                style = Stroke(
                    width = 1.5.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 5.dp.toPx())),
                ),
            )
            drawPoint(start, RxfColors.Green)
            drawPoint(end, RxfColors.Orange)
        }

        // chip da rota ao lado do ponto de partida
        Row(
            modifier = Modifier
                .offset(x = w * 0.12f + 14.dp, y = h * 0.80f - 44.dp)
                .shadow(8.dp, RoundedCornerShape(10.dp), ambientColor = RxfColors.GreenRing, spotColor = RxfColors.GreenRing)
                .clip(RoundedCornerShape(10.dp))
                .background(RxfColors.Surface)
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Column {
                Text("Base → Localidades", fontFamily = fonts.body, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RxfColors.Ink)
                Text("Rota otimizada · 8,4 km", fontSize = 10.sp, color = Color(0xFF7C9785))
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawPoint(center: Offset, color: Color) {
    drawCircle(Color.White, 8.dp.toPx(), center)
    drawCircle(color, 8.dp.toPx(), center, style = Stroke(2.dp.toPx()))
    drawCircle(color, 4.dp.toPx(), center)
}

@Composable
private fun RxfTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailing: (@Composable () -> Unit)? = null,
) {
    Column {
        Text(
            label,
            color = RxfColors.Label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 7.dp),
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontFamily = LocalRxfFonts.current.body, fontSize = 14.sp, color = RxfColors.Ink),
            placeholder = { Text(placeholder, color = RxfColors.Placeholder, fontSize = 14.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            trailingIcon = trailing,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RxfColors.Green,
                unfocusedBorderColor = RxfColors.InputBorder,
                focusedContainerColor = RxfColors.Surface,
                unfocusedContainerColor = RxfColors.Surface,
                cursorColor = RxfColors.Green,
            ),
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    RouteXFlowTheme { LoginScreen() }
}
