package com.musaecer.personalfinance.presentation.view.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.musaecer.personalfinance.presentation.navigation.Navigation
import com.musaecer.personalfinance.presentation.navigation.Screen

@Composable
fun BottomAppBar(modifier: Modifier = Modifier, navController: NavController) {

    val selectedIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val navigationItems = listOf(
        Navigation(
            title = "Home",
            icon = Icons.Default.Home,
            route = Screen.HomeScreen.route
        ),
        Navigation(
            title = "Expenses",
            icon = Icons.Default.Info,
            route = Screen.ExpenseListScreen.route
        ),
        Navigation(
            title = "Admin",
            icon = Icons.Default.Person,
            route = Screen.AdminScreen.route
        ),

    )

    NavigationBar(

    ) { navigationItems.forEachIndexed{ index, item ->
        NavigationBarItem(
            selected = selectedIndex.intValue == index,
            onClick = {
                selectedIndex.intValue = index
                navController.navigate(item.route)
            },
            icon = {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title
                )
            },
            label = {
                Text(
                    item.title,
                    color = if(index == selectedIndex.intValue){
                        Color.Black
                    }
                    else{
                        Color.Gray
                    }
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.surface,
                indicatorColor = MaterialTheme.colorScheme.primary
            )
        )
    }

    }
}