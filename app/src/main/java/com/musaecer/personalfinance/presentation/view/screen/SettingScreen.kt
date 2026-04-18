package com.musaecer.personalfinance.presentation.view.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.musaecer.personalfinance.presentation.viewmodel.CategoryViewModel
import com.musaecer.personalfinance.presentation.viewmodel.PaymentMethodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    paymentViewModel: PaymentMethodViewModel = hiltViewModel()
) {
    // State'ler
    var categoryName by remember { mutableStateOf("") }
    var paymentMethodName by remember { mutableStateOf("") }
    var paymentDescription by remember { mutableStateOf("") }
    var paymentType by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ayarlar", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Ekran küçükse kaydırılabilir olsun
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // --- KATEGORİ EKLEME BÖLÜMÜ ---
            SectionCard(title = "Kategori Yönetimi", icon = Icons.Default.Edit) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = categoryName,
                        onValueChange = { categoryName = it },
                        label = { Text("Kategori Adı") },
                        placeholder = { Text("Örn: Market, Eğitim...") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            if (categoryName.isNotBlank()) {
                                categoryViewModel.insertCategory(categoryName)
                                categoryName = "" // İşlemden sonra temizle
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = categoryName.isNotBlank()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Kategori Ekle")
                    }
                }
            }

            // --- ÖDEME YÖNTEMİ EKLEME BÖLÜMÜ ---
            SectionCard(title = "Ödeme Yöntemi", icon = Icons.Default.Star) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = paymentMethodName,
                        onValueChange = { paymentMethodName = it },
                        label = { Text("Yöntem Adı") },
                        placeholder = { Text("Örn: Bonus Kartım") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = paymentDescription,
                        onValueChange = { paymentDescription = it },
                        label = { Text("Açıklama") },
                        placeholder = { Text("Örn: Visa - 4242") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = paymentType,
                        onValueChange = { paymentType = it },
                        label = { Text("Tür") },
                        placeholder = { Text("Örn: Kredi Kartı") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            if (paymentMethodName.isNotBlank()) {
                                paymentViewModel.insertPaymentMethod(
                                    name = paymentMethodName,
                                    description = paymentDescription,
                                    type = paymentType
                                )
                                // Temizlik
                                paymentMethodName = ""; paymentDescription = ""; paymentType = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = paymentMethodName.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Ödeme Yöntemi Ekle")
                    }
                }
            }
        }
    }
}

// Ortak Kart Tasarımı (Reusable Component)
@Composable
fun SectionCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(12.dp))
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}