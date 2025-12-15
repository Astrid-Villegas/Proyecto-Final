package com.example.loginsigo.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info // Icono genérico seguro
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginsigo.ui.theme.TextBlack
import com.example.loginsigo.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Historial Académico", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Atrás", tint = TextBlack)
                    }
                }
            )
        },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SubjectCard(
                    title = "Inglés IV",
                    subtitle = "Lic. Marco Antonio Suarez",
                    status = "Activo",
                    statusColor = Color(0xFF66BB6A),
                    details = listOf("El pasado" to "E", "Pasado Simple vs Continuo" to "E"),
                    progress = "100%"
                )
            }
            item {
                SubjectCard(
                    title = "Desarrollo de Apps Móvil",
                    subtitle = "Ing. Nelson Crozby Padilla",
                    status = "Finalizado",
                    statusColor = TextGray,
                    details = listOf("Intro a desarrollo" to "A", "Diseño de apps" to "B"),
                    progress = "100%"
                )
            }
        }
    }
}

@Composable
fun SubjectCard(
    title: String,
    subtitle: String,
    status: String,
    statusColor: Color,
    details: List<Pair<String, String>>,
    progress: String
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.Info, null, modifier = Modifier.size(24.dp), tint = TextBlack)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBlack)
                    Text(subtitle, fontSize = 12.sp, color = TextGray)
                }
                Surface(
                    color = statusColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(status, color = statusColor, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
            Divider(modifier = Modifier.padding(vertical = 12.dp))
            details.forEach { (materia, calif) ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(materia, fontSize = 12.sp, color = TextBlack, maxLines = 1, modifier = Modifier.weight(1f))
                    Text(calif, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column { Text("Progreso:", fontSize = 10.sp, color = TextGray); Text(progress, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                Column(horizontalAlignment = Alignment.End) { Text("Evaluación:", fontSize = 10.sp, color = TextGray); Text("Ordinaria", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
            }
        }
    }
}