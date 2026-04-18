package com.musaecer.personalfinance.presentation.view

import AddExpenseScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.musaecer.personalfinance.presentation.navigation.Screen
import com.musaecer.personalfinance.presentation.view.component.BottomAppBar
import com.musaecer.personalfinance.presentation.view.screen.AdminScreen
import com.musaecer.personalfinance.presentation.view.screen.CalenderScreen
import com.musaecer.personalfinance.presentation.view.screen.ExpenseScreen
import com.musaecer.personalfinance.presentation.view.screen.HomeScreen
import com.musaecer.personalfinance.presentation.view.screen.SettingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Personal Finance",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    )
                },
                navigationIcon = {
                    // Eğer ana sayfada değilsek geri butonu görünsün
                    if (currentRoute != Screen.HomeScreen.route) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Geri"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = { BottomAppBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.ExpenseListScreen.route) {
                ExpenseScreen()
            }
            composable(Screen.RevenueListScreen.route) {
                Text("Revenues Screen")
            }
            composable(Screen.AddExpenseScreen.route) {
                AddExpenseScreen(navController = navController)
            }
            composable(Screen.AdminScreen.route) {
                AdminScreen(navController)
            }
            composable(Screen.CalenderScreen.route) {
                CalenderScreen()
            }
            composable(Screen.SettingScreen.route) {
                SettingScreen(navController)
            }
        }
    }
}