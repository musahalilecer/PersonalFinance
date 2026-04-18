package com.musaecer.personalfinance.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Göz yormayan Açık Tema Renk Paleti
private val SoftWhite = Color(0xFFF8F9FA) // Arka plan
private val PureWhite = Color(0xFFFFFFFF) // Kartlar
private val TextPrimary = Color(0xFF1A1C1E) // Ana metin
private val TextSecondary = Color(0xFF6C757D) // Yardımcı metin
private val AccentGreen = Color(0xFF2E7D32) // Başarı ve vurgu yeşili
private val SoftGreenBg = Color(0xFFE8F5E9) // Yeşil vurgu arka planı
private val BorderColor = Color(0xFFE9ECEF) // Hafif kenarlıklar

@Composable
fun AdminScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = SoftWhite,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            item { AnalysisHeader(navController) }
            item { TimePeriodFilter() }
            item { MainChartCard() }
            item { FinancialInsightsSection() }

            item { TopCategoriesHeader() }

            val categories = listOf(
                CategoryData("Gıda ve Yemek", "₺1.240,00", 0.7f, Color(0xFF4CAF50)),
                CategoryData("Faturalar", "₺1.850,00", 0.95f, Color(0xFFE53935)),
                CategoryData("Ulaşım", "₺420,00", 0.35f, Color(0xFF1E88E5))
            )

            itemsIndexed(categories) { _, item ->
                CategoryProgressItem(item)
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun AnalysisHeader(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Analiz", style = MaterialTheme.typography.headlineSmall, color = TextPrimary, fontWeight = FontWeight.Bold)
            Text("Yönetici Paneli", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }
        Surface(
            color = PureWhite,
            shape = CircleShape, // Eğer butonlar yayılacaksa CircleShape yerine RoundedCornerShape(20.dp) daha iyi durabilir
            shadowElevation = 2.dp,
            // Genişliği içeriğe göre esnetmek için fixed size yerine padding verelim
            modifier = Modifier.wrapContentSize()
        ) {
            Row(
                // Butonlar arasına 8dp boşluk ekler
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp) // Kenarlardan biraz nefes payı
            ) {
                IconButton(onClick = { navController.navigate("calender_screen") }) {
                    Icon(Icons.Default.DateRange, contentDescription = null, tint = TextPrimary)
                }
                IconButton(onClick = { navController.navigate("setting_screen") }) {
                    Icon(Icons.Default.Settings, contentDescription = null, tint = TextPrimary)
                }
            }
        }
    }
}

@Composable
fun TimePeriodFilter() {
    var selectedPeriod by remember { mutableStateOf("Gün") }
    val periods = listOf("Gün", "Hafta", "Ay", "Yıl")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE9ECEF), RoundedCornerShape(24.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        periods.forEach { period ->
            val isSelected = selectedPeriod == period
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) PureWhite else Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { selectedPeriod = period }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = period,
                    color = if (isSelected) TextPrimary else TextSecondary,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun MainChartCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Toplam Harcama", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                    Text("₺3.842,00", color = TextPrimary, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                }
                Surface(color = SoftGreenBg, shape = RoundedCornerShape(12.dp)) {
                    Text("↘ %12", color = AccentGreen, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(190.dp)) {
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 18.dp,
                    color = Color(0xFFF1F3F5),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                CircularProgressIndicator(
                    progress = { 0.78f },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 18.dp,
                    color = AccentGreen,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Bütçe Kotası", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
                    Text("%78", color = TextPrimary, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FinancialInsightsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Finansal Özet", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            InsightCard(
                title = "Tasarruf Hedefi",
                desc = "%80 tamamlandı",
                icon = Icons.Default.Star,
                tint = Color(0xFFFBC02D),
                modifier = Modifier.weight(1f)
            )
            InsightCard(
                title = "Bütçe Uyarısı",
                desc = "Eğlence harcaması yüksek",
                icon = Icons.Default.Notifications,
                tint = Color(0xFFD32F2F),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun InsightCard(title: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(Modifier.background(tint.copy(0.1f), CircleShape).padding(8.dp)) {
                Icon(icon, null, modifier = Modifier.size(18.dp), tint = tint)
            }
            Spacer(Modifier.height(12.dp))
            Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(desc, color = TextSecondary, fontSize = 12.sp, lineHeight = 16.sp)
        }
    }
}

@Composable
fun TopCategoriesHeader() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text("En Çok Harcananlar", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
        Text("Tümü", color = AccentGreen, fontWeight = FontWeight.Bold, modifier = Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { })
    }
}

@Composable
fun CategoryProgressItem(data: CategoryData) {
    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(data.title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(data.amount, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { data.progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
            color = data.color,
            trackColor = Color(0xFFF1F3F5)
        )
    }
}

data class CategoryData(
    val title: String,
    val amount: String,
    val progress: Float,
    val color: Color
)