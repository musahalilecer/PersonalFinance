package com.musaecer.personalfinance.presentation.navigation

sealed class Screen(
    val route: String
){
    object HomeScreen: Screen("home_screen")
    object AddExpenseScreen: Screen("add_expense_screen")
    object AddRevenueScreen: Screen("add_revenue_screen")
    object ExpenseListScreen: Screen("expense_list_screen")
    object RevenueListScreen: Screen("revenue_list_screen")
    object AdminScreen: Screen("admin_screen")
    object CalenderScreen: Screen("calender_screen")
    object SettingScreen: Screen("setting_screen")
}
