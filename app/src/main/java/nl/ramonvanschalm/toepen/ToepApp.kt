package nl.ramonvanschalm.toepen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import nl.ramonvanschalm.toepen.navigation.Screen
import nl.ramonvanschalm.toepen.ui.screens.SpelersScreen
import nl.ramonvanschalm.toepen.ui.screens.ToepScreen
import nl.ramonvanschalm.toepen.ui.theme.MaroonDark
import nl.ramonvanschalm.toepen.ui.theme.White
import nl.ramonvanschalm.toepen.viewmodel.ToepViewModel

data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector
)

@Composable
fun ToepApp() {
    val navController = rememberNavController()
    val viewModel: ToepViewModel = viewModel()

    val navItems = listOf(
        BottomNavItem(Screen.Toepen, Icons.Filled.Style),
        BottomNavItem(Screen.Spelers, Icons.Filled.People)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaroonDark,
                contentColor = White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.screen.label
                            )
                        },
                        label = { Text(item.screen.label) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == item.screen.route
                        } == true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = White,
                            selectedTextColor = White,
                            unselectedIconColor = White.copy(alpha = 0.5f),
                            unselectedTextColor = White.copy(alpha = 0.5f),
                            indicatorColor = MaroonDark.copy(alpha = 0.3f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Toepen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Toepen.route) {
                ToepScreen(viewModel = viewModel)
            }
            composable(Screen.Spelers.route) {
                SpelersScreen(viewModel = viewModel)
            }
        }
    }
}
