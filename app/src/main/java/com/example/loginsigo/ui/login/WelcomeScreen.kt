package com.example.loginsigo.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginsigo.data.model.UserResponse
import com.example.loginsigo.ui.theme.SigoGreen
import com.example.loginsigo.ui.theme.SigoGreenLight
import com.example.loginsigo.ui.theme.TextBlack
import com.example.loginsigo.ui.theme.TextGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    user: UserResponse,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        // scrimColor es el color oscuro que cubre la pantalla al abrir el menú
        scrimColor = Color.Black.copy(alpha = 0.32f),
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                // ¡IMPORTANTE! Este width fijo evita el error de la pantalla blanca gigante
                modifier = Modifier.width(300.dp)
            ) {
                // Header
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("SIGO", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = SigoGreen)
                    Text("Menú Principal", fontSize = 14.sp, color = TextGray)
                }
                Divider(modifier = Modifier.padding(horizontal = 24.dp))
                Spacer(modifier = Modifier.height(16.dp))

                // Items
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Home, null) },
                    colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = SigoGreenLight, selectedIconColor = SigoGreen, selectedTextColor = SigoGreen),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Historial Académico") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onHistoryClick()
                    },
                    icon = { Icon(Icons.Default.List, null) },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Mi Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onProfileClick()
                    },
                    icon = { Icon(Icons.Default.AccountBox, null) },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
                Divider(modifier = Modifier.padding(horizontal = 24.dp))

                NavigationDrawerItem(
                    label = { Text("Cerrar Sesión") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onLogoutClick()
                    },
                    icon = { Icon(Icons.Default.ExitToApp, null) },
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    ) {
        // Contenido Principal
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(user.username, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                // Abrir solo si está cerrado para evitar doble acción
                                if (drawerState.isClosed) drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, "Menú")
                        }
                    },
                    actions = {
                        IconButton(onClick = onProfileClick) {
                            Icon(Icons.Outlined.AccountCircle, "Perfil", modifier = Modifier.size(28.dp))
                        }
                    }
                )
            },
            containerColor = Color.White
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxSize()
            ) {
                Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(bottom = 32.dp)) {
                    // Aquí también mostramos el primer nombre para el saludo casual
                    val primerNombre = user.personFullName.split(" ").firstOrNull() ?: user.personFullName
                    Text("Hola, $primerNombre", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextBlack)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(user.profileName, fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(bottom = 2.dp))
                }

                MenuCard("Historial Académico", Icons.Default.List, onHistoryClick)
                Spacer(modifier = Modifier.height(16.dp))
                MenuCard("Mi Perfil", Icons.Default.AccountBox, onProfileClick)
            }
        }
    }
}

@Composable
fun MenuCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth().height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = TextBlack, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextBlack, modifier = Modifier.weight(1f))
            Icon(Icons.Default.ArrowForward, null, tint = TextBlack)
        }
    }
}