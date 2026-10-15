package com.vntransportltda.app_routexflow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vntransportltda.app_routexflow.ui.login.LoginScreen
import com.vntransportltda.app_routexflow.ui.login.LoginViewModel
import com.vntransportltda.app_routexflow.ui.theme.RouteXFlowTheme

@Composable
@Preview
fun App() {
    RouteXFlowTheme {
        val viewModel = viewModel { LoginViewModel() }
        val state by viewModel.state.collectAsState()

        if (state.loggedIn) {
            // Placeholder até existir a tela inicial do app.
            Box(Modifier.fillMaxSize().safeContentPadding(), contentAlignment = Alignment.Center) {
                Text("Olá, ${state.user?.name ?: "motorista"}!")
            }
        } else {
            LoginScreen(
                onLogin = viewModel::login,
                error = state.error,
                loading = state.loading,
            )
        }
    }
}
