package com.loc.forsales.presentation.navgraph


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Home.HomeScreen
import com.loc.forsales.presentation.detail.DetailScreen
import com.loc.forsales.presentation.detail.DetailsViewModel
import com.loc.forsales.presentation.news_navigator.NewsNavigator

@Composable
fun NavGraph(
    startDestination: String = Route.HomeScreen.route
) {
    val navController = rememberNavController() // Initialize NavController

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Route.HomeScreen.route) {
            // Use `rememberSaveable` to preserve HomeScreen state across navigation
            HomeScreen(
                navigateToDetails = { product ->
                    navigateToDetails(
                        navController = navController,
                        product = product
                    )
                }
            )
        }

        composable(route = Route.DetailsScreen.route) {
            val viewModel: DetailsViewModel = hiltViewModel()

            // Retrieve the product passed in the savedStateHandle
            val product = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Product?>("product")

            Log.d("product_id", product?.productID.toString())

            // Trigger fetching the product if it exists
            LaunchedEffect(product) {
                product?.let {
                    viewModel.getProduct(it.productID)
                }
            }

            // Access the state value directly
            val state = viewModel.state.value

            // Display the DetailScreen without passing the HomeScreen as a composable
            DetailScreen(
                products = state.products,
            )
        }
    }
}

// Helper function to navigate to the details screen
private fun navigateToDetails(navController: NavController, product: Product) {
    navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
    navController.navigate(Route.DetailsScreen.route)
}