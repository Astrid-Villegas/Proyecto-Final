package com.example.loginsigo.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginsigo.data.model.UserResponse
import com.example.loginsigo.ui.theme.SigoGreen
import com.example.loginsigo.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: UserResponse,
    onBackClick: () -> Unit
) {
    // Cálculo de iniciales
    val iniciales = user.personFullName
        .split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")
        .uppercase()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, "Atrás") }
                }
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // --- TARJETA DE CABECERA ---
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                    // Iniciales
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFC8E6C9),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = iniciales,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(user.personFullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))

                        // Perfil + Badge
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Perfil: ${user.profileName}", fontSize = 12.sp, color = TextGray)
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(4.dp)) {
                                Row(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("Verificado", fontSize = 10.sp, color = SigoGreen)
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Icon(Icons.Default.CheckCircle, null, tint = SigoGreen, modifier = Modifier.size(10.dp))
                                }
                            }
                        }

                        Text("Usuario: ${user.username}", fontSize = 12.sp, color = TextGray)

                        Row(modifier = Modifier.padding(top = 4.dp)) {
                            Text("Contraseña: ", fontSize = 12.sp, color = TextGray)
                            Text(
                                "CAMBIAR",
                                fontSize = 12.sp,
                                color = SigoGreen,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { /* Acción cambiar */ }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- FORMULARIO ---
            Text("Información Personal:", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            ProfileField("Nombre Completo:", user.personFullName)
            ProfileField("Correo Electrónico:", user.email)
            ProfileField("CURP:", "")
            ProfileField("NSS:", "")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SigoGreen),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("ACTUALIZAR", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(label, fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = SigoGreen
            ),
            singleLine = true
        )
    }
}