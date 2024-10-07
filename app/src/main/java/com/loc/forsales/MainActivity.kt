package com.loc.forsales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.loc.forsales.presentation.navgraph.NavGraph
import com.loc.forsales.presentation.Home.HomeScreen
import com.loc.forsales.ui.theme.ForSalesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForSalesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Create NavHostController and pass it to NavGraph
                    val navController = rememberNavController()

                    NavGraph(
                        navController = navController, // Pass NavController here
                        startDestination = viewModel.startDestination.value // Ensure correct start destination
                    )
                }
            }
        }
    }
}
