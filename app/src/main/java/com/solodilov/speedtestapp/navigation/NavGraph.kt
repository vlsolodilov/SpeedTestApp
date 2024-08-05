package com.solodilov.speedtestapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.solodilov.presentation.screen.settings.ui.SettingsScreen
import com.solodilov.presentation.screen.speedtest.ui.SpeedTestScreen
import com.solodilov.util.navigation.Routes

@Composable
fun NavGraph(
    navController: NavHostController,
    padding: PaddingValues,
) {
    val animationSpec: FiniteAnimationSpec<IntOffset> = tween(400)

    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = Routes.SpeedTest
    ) {

        composable(
            route = Routes.SpeedTest,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.Settings -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec
                    )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.Settings -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec
                    )

                    else -> null
                }
            },
        ) {
            SpeedTestScreen()
        }
        composable(
            route = Routes.Settings,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.SpeedTest -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec
                    )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.SpeedTest -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec
                    )

                    else -> null
                }
            },
        ) {
            SettingsScreen()
        }
    }
}