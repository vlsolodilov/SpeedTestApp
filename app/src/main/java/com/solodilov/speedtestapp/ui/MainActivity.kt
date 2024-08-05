package com.solodilov.speedtestapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.solodilov.speedtestapp.navigation.BottomNavBar
import com.solodilov.speedtestapp.navigation.NavGraph
import com.solodilov.util.annotation.ThemeMode
import com.solodilov.util.theme.DarkColorScheme
import com.solodilov.util.theme.LightColorScheme
import com.solodilov.util.theme.Typography
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModel<MainViewModel>()
        setContent {
            val navController = rememberNavController()

            val colorScheme = if (
                viewModel.themeMode.value == ThemeMode.DARK
                || isSystemInDarkTheme()
            ) {
                DarkColorScheme
            } else {
                LightColorScheme
            }
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        padding = innerPadding,
                    )
                }
            }
        }
    }
}