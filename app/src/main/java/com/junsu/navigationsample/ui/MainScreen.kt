package com.junsu.navigationsample.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.junsu.navigationsample.ui.home.HomeScreen
import com.junsu.navigationsample.ui.profile.ProfileDetailScreen
import com.junsu.navigationsample.ui.profile.ProfileScreen
import com.junsu.navigationsample.ui.search.SearchScreen

@Composable
fun MainScreen() {
    val rootNavController = rememberNavController()

    NavHost(navController = rootNavController, startDestination = "main") {
        composable("main") {
            MainTabContent()
        }
        composable(
            "fullscreen",
            deepLinks = listOf(navDeepLink { uriPattern = "junsu://fullscreen" })
        ) {
            FullScreen()
        }
    }
}

@Composable
fun MainTabContent() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavHost(navController = navController)
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { backStackEntry ->
            HomeScreen(
                onMoveProfileDetail = {
                    navController.navigate("profile/detail/HomeScreen")
                }
            )
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen()
        }
        composable(BottomNavItem.Profile.route) { backStackEntry ->
            ProfileScreen(onMoveProfileDetail = {
                navController.navigate("profile/detail/ProfileScreen")
            })
        }
        composable(
            "profile/detail/{from}",
            arguments = listOf(navArgument("from") {
                type = NavType.StringType
                defaultValue = "Empty"
            })
        ) { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from") ?: "Empty"
            ProfileDetailScreen(from = from, navController)
        }

    }
}
