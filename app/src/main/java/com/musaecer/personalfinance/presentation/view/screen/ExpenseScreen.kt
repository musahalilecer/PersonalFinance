package com.musaecer.personalfinance.presentation.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musaecer.personalfinance.domain.model.Expense
import com.musaecer.personalfinance.presentation.viewmodel.ExpenseViewModel

@Composable
fun ExpenseScreen(modifier: Modifier = Modifier) {
    val expenseViewModel: ExpenseViewModel = hiltViewModel()

    // .value yerine 'by' kullanarak state'i dinliyoruz
    val state by expenseViewModel.state

    LaunchedEffect(key1 = true) {
        expenseViewModel.getExpenses()
    }

    Column(modifier = modifier.fillMaxSize()) {
        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (state.expenses.isEmpty() && !state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Henüz harcama eklenmemiş.")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.expenses) { expense ->
                ExpenseItem(
                    modifier = Modifier.fillMaxWidth(),
                    expense = expense
                )
            }
        }
    }
}

@Composable
fun ExpenseItem(modifier: Modifier = Modifier, expense: Expense) {
    Card(modifier = modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = expense.description)
            Text(text = "Amount: ${expense.amount}")
            Text(text = "Date: ${expense.date}")
        }
    }

}