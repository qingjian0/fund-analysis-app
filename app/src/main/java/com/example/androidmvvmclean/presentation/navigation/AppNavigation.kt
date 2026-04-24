package com.example.androidmvvmclean.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidmvvmclean.presentation.ui.AnalyzeScreen
import com.example.androidmvvmclean.presentation.ui.CompareScreen
import com.example.androidmvvmclean.presentation.ui.HomeScreen
import com.example.androidmvvmclean.presentation.ui.PortfolioScreen
import com.example.androidmvvmclean.presentation.ui.ScanScreen
import com.example.androidmvvmclean.presentation.ui.SearchScreen
import com.example.androidmvvmclean.presentation.ui.SettingsScreen
import com.example.androidmvvmclean.presentation.viewmodel.SettingsViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Analyze : Screen("analyze/{code}") {
        fun createRoute(code: String) = "analyze/$code"
    }
    object Search : Screen("search")
    object Compare : Screen("compare")
    object Portfolio : Screen("portfolio")
    object Scan : Screen("scan")
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Analyze.route) {backStackEntry ->
            val code = backStackEntry.arguments?.getString("code") ?: ""
            AnalyzeScreen(navController = navController, fundCode = code)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.Compare.route) {
            CompareScreen(navController = navController)
        }
        composable(Screen.Portfolio.route) {
            PortfolioScreen(navController = navController)
        }
        composable(Screen.Scan.route) {
            ScanScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(navController = navController, viewModel = viewModel)
        }
    }
}
