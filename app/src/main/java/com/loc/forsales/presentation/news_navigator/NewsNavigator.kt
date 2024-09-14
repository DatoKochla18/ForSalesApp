package com.loc.forsales.presentation.news_navigator

import androidx.compose.runtime.collectAsState

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Home.HomeScreen
import com.loc.forsales.presentation.detail.DetailScreen
import com.loc.forsales.presentation.detail.DetailsViewModel
import com.loc.forsales.presentation.navgraph.Route

import androidx.navigation.NavHostController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Bottom bar content
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            Modifier.padding(innerPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
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

                // Trigger fetching the product if it exists
                LaunchedEffect(product) {
                    product?.let {
                        viewModel.getProduct(it.productID)
                    }
                }

                // Access the state value directly
                val state = viewModel.state.value

                DetailScreen(
                    products = state.products
                )
            }
        }
    }
}






private fun navigateToDetails(navController: NavController, product: Product) {
    navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
    navController.navigate(Route.DetailsScreen.route)
}
