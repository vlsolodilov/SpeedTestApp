package com.solodilov.speedtestapp.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.solodilov.util.R
import com.solodilov.util.navigation.Routes

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val items = listOf(
        NavItem(
            route = Routes.SpeedTest,
            icon = painterResource(id = R.drawable.ic_speed),
            label = stringResource(id = R.string.test),
        ),
        NavItem(
            route = Routes.Settings,
            icon = painterResource(id = R.drawable.ic_settings),
            label = stringResource(id = R.string.settings),
        )
    )
    @Composable
    fun String.isSelected() =
        navController.currentBackStackEntryAsState().value?.destination?.route == this

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
    ) {

        items.forEach { item ->
            val isSelected = item.route.isSelected()
            NavigationBarItem(
                icon = { Icon(painter = item.icon, contentDescription = null) },
                label = { Text(item.label) },
                selected = isSelected,
                onClick = { if (!isSelected) navController.navigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary
                ),
            )
        }
    }
}

data class NavItem(
    val route: String,
    val icon: Painter,
    val label: String
)

