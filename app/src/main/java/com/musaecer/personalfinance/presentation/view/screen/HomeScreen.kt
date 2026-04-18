package com.musaecer.personalfinance.presentation.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.musaecer.personalfinance.presentation.view.component.SearchBar
import com.musaecer.personalfinance.presentation.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    // ViewModel'deki state'i gözlemliyoruz
    val state = viewModel.state.value
    val expenses = state.expenses

    // Basit bir hesaplama mantığı (Gelen verilere göre bakiye, gelir, gider)
    val totalIncome = expenses.filter { it.amount > 0 }.sumOf { it.amount }
    val totalExpense = expenses.filter { it.amount < 0 }.sumOf { it.amount }
    val totalBalance = totalIncome + totalExpense

    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Hoş Geldin!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                SearchBar { /* Arama mantığı - viewModel.getExpensesById vb. */ }
            }
        },
        floatingActionButton = {
            IconButton(
                onClick = {
                    navController.navigate("add_expense_screen")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Expense"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ana Bakiye Kartı - Gerçek veriyle güncellendi
            BalanceCard(
                balance = "₺${String.format("%.2f", totalBalance)}",
                modifier = Modifier.fillMaxWidth()
            )

            // Gelir ve Gider Özet Kartları
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    title = "Gelirler",
                    amount = "₺${String.format("%.2f", totalIncome)}",
                    containerColor = Color(0xFFE8F5E9),
                    contentColor = Color(0xFF2E7D32),
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Giderler",
                    amount = "₺${String.format("%.2f", Math.abs(totalExpense))}",
                    containerColor = Color(0xFFFFEBEE),
                    contentColor = Color(0xFFC62828),
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Son İşlemler",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            // Liste - ViewModel'den gelen listeyi gönderiyoruz
            RecentOperations(
                expenses = expenses,
                modifier = Modifier.weight(1f)
            )

            // Loading göstergesi (Opsiyonel)
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun BalanceCard(balance: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Toplam Bakiye", style = MaterialTheme.typography.labelLarge)
            Text(
                text = balance,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun RecentOperations(
    expenses: List<com.musaecer.personalfinance.domain.model.Expense>, // Kendi Model sınıfın
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(expenses.size) { index ->
            val expense = expenses[index]
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(expense.description, fontWeight = FontWeight.Medium)
                        Text(expense.date, style = MaterialTheme.typography.bodySmall)
                    }
                    Text(
                        text = "₺${expense.amount}",
                        color = if (expense.amount < 0) Color.Red else Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    amount: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = contentColor)
            Text(amount, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = contentColor)
        }
    }
}

/*
@Composable
fun RecentOperations(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(15) { index ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Market Alışverişi #$index", fontWeight = FontWeight.Medium)
                        Text("14 Şubat 2026", style = MaterialTheme.typography.bodySmall)
                    }
                    Text(
                        text = if (index % 2 == 0) "-₺100" else "+₺250",
                        color = if (index % 2 == 0) Color.Red else Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

 */