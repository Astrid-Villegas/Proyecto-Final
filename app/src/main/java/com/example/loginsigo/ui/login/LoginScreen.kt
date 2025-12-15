package com.example.loginsigo.ui.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginsigo.data.model.UserResponse
import com.example.loginsigo.ui.theme.SigoGreen
import com.example.loginsigo.ui.theme.TextGray

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (UserResponse) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // EFECTO: Si el login es exitoso
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess && uiState.user != null) {
            // --- AQUÍ ESTÁ EL CAMBIO ---
            // Muestra: "Bienvenido a SIGO.... Marco Antonio Aviña Jimenez"
            Toast.makeText(
                context,
                "Bienvenido a SIGO.... ${uiState.user?.personFullName}",
                Toast.LENGTH_LONG
            ).show()

            onLoginSuccess(uiState.user!!)
        }
    }

    // EFECTO: Si hay error
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("SIGO", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = SigoGreen)
            Text("Sistema Integral de Gestión y Operación", fontSize = 14.sp, color = TextGray, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp, bottom = 48.dp))

            OutlinedTextField(
                value = uiState.username,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                trailingIcon = { Icon(Icons.Default.Person, null) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SigoGreen, focusedLabelColor = SigoGreen, cursorColor = SigoGreen)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = { Icon(Icons.Default.Lock, null) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SigoGreen, focusedLabelColor = SigoGreen, cursorColor = SigoGreen)
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(color = SigoGreen)
            } else {
                Button(
                    onClick = { viewModel.login() },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SigoGreen)
                ) {
                    Text("INICIAR SESIÓN", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("¿Olvidaste tu contraseña?", color = Color.Black, fontSize = 14.sp, textDecoration = TextDecoration.Underline, modifier = Modifier.clickable {})
        }
    }
}
