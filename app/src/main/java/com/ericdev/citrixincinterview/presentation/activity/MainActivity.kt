package com.ericdev.citrixincinterview.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ericdev.citrixincinterview.presentation.component.BottomNavItem
import com.ericdev.citrixincinterview.presentation.screen.NavGraphs
import com.ericdev.citrixincinterview.presentation.screen.destinations.AllUsersDestination
import com.ericdev.citrixincinterview.presentation.screen.destinations.ProfileDestination
import com.ericdev.citrixincinterview.presentation.theme.AppTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalMaterialNavigationApi::class,
        ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                var showBottomBar by remember { mutableStateOf(true) }

                val navController = rememberAnimatedNavController()
                val navHostEngine = rememberAnimatedNavHostEngine(
                    navHostContentAlignment = Alignment.TopCenter,
                    rootDefaultAnimations = RootNavGraphDefaultAnimations(
                        enterTransition = {
                            scaleIn(transformOrigin = TransformOrigin(0.25f, 0f))
                        },
                        exitTransition = {
                            scaleOut(transformOrigin = TransformOrigin(0.75f, 1f))
                        }
                    )
                )

                val systemUiController = rememberSystemUiController()
                val statusBarColor = MaterialTheme.colorScheme.surface
                val useDarkIcons = isSystemInDarkTheme().not()

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = true // useDarkIcons
                    )
                }

                val newBackStackEntry by navController.currentBackStackEntryAsState()
                val route = newBackStackEntry?.destination?.route

                val bottomBarItems: List<BottomNavItem> = listOf(
                    BottomNavItem.Profile,
                    BottomNavItem.AllUsers,
                )

                showBottomBar = route in listOf(
                    ProfileDestination.route,
                    AllUsersDestination.route
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigation(
                                modifier = Modifier
                                    .padding(horizontal = 50.dp, vertical = 28.dp)
                                    .clip(RoundedCornerShape(100))
                                    .background(Color.Transparent)
                                    .height(62.dp),
                                elevation = 0.dp,
                                backgroundColor = MaterialTheme.colorScheme.surface
                            ) {
                                val navBackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackEntry?.destination

                                bottomBarItems.forEach { item ->
                                    val selected =
                                        currentDestination?.route?.contains(item.destination.route) == true

                                    BottomNavigationItem(
                                        modifier = Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer),
                                        icon = {
                                            Box(
                                                modifier = Modifier.fillMaxSize(.62F),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                if (selected) {
                                                    Icon(
                                                        modifier = Modifier,
                                                        tint = MaterialTheme.colorScheme.onSurface,
                                                        painter = painterResource(id = item.selectedIcon),
                                                        contentDescription = "Nav icon"
                                                    )
                                                } else {
                                                    Icon(
                                                        painter = painterResource(id = item.unselectedIcon),
                                                        contentDescription = "Icon",
                                                        tint = MaterialTheme.colorScheme.onSurface.copy(
                                                            alpha = .75F
                                                        )
                                                    )
                                                }
                                            }
                                        },
                                        alwaysShowLabel = false,
                                        selectedContentColor = MaterialTheme.colorScheme.onSurface,
                                        selected = currentDestination?.route?.contains(item.destination.route) == true,
                                        onClick = {
                                            navController.navigate(item.destination.route) {
                                                navController.graph.startDestinationRoute?.let { route ->
                                                    popUpTo(route) {
                                                        saveState = true
                                                    }
                                                }
                                                restoreState = true
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) {
                    val padding = it

                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        navController = navController,
                        engine = navHostEngine
                    )
                }
            }
        }
    }
}
