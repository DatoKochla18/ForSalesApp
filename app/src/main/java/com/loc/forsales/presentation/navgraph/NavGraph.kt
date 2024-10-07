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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Entry.EntryScreen
import com.loc.forsales.presentation.Home.HomeScreen
import com.loc.forsales.presentation.cart.ProductCartScreen
import com.loc.forsales.presentation.cart.ProductCartViewModel
import com.loc.forsales.presentation.detail.DetailScreen
import com.loc.forsales.presentation.detail.DetailsViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Route.EntryScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Route.EntryScreen.route) {
            EntryScreen(
                onCategorySelected = { categoryId ->
                    navController.navigate("${Route.HomeScreen.route}/$categoryId")
                }
            )
        }

        composable(
            route = "${Route.HomeScreen.route}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: -1
            HomeScreen(
                categoryId = categoryId,
                navigateToDetails = { product ->
                    navigateToDetails(navController, product)
                },
                navigateToCart = { cartIds ->
                    navController.navigate("${Route.CartScreen.route}/$categoryId/$cartIds")
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
        composable(
            route = "${Route.CartScreen.route}/{categoryId}/{cartIds}",
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("cartIds") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: -1
            val cartIds = backStackEntry.arguments?.getString("cartIds") ?: ""
            val viewModel: ProductCartViewModel = hiltViewModel()

            LaunchedEffect(categoryId, cartIds) {
                viewModel.searchProductCart( cartIds,categoryId)
            }

            ProductCartScreen(
                viewModel = viewModel,

            )
        }
    }
    }


// Helper function to navigate to the details screen
private fun navigateToDetails(navController: NavController, product: Product) {
    navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
    navController.navigate(Route.DetailsScreen.route)
}