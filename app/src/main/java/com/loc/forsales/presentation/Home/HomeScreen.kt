package com.loc.forsales.presentation.Home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Home.components.CategoryFilter
import com.loc.forsales.presentation.Home.components.ProductList
import com.loc.forsales.presentation.Home.components.SearchBox
import com.loc.forsales.ui.theme.ForSalesTheme
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    categoryId: Int,
    navigateToDetails: (Product) -> Unit,
    navigateToCart: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Use LaunchedEffect to react to the categoryId parameter
    LaunchedEffect(categoryId) {
        viewModel.loadProductsByCategory(categoryId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    val cartIds = viewModel.fetchCartIds()
                    navigateToCart(cartIds)
                }
            ) {
                Text("Go to Cart")
            }

            SearchBox(
                query = state.query,
                onQueryChanged = { query ->
                    viewModel.updateQuery(query,categoryId)
                },
                onSearch = {
                    viewModel.searchProducts(state.query,categoryId)
                }
            )
        }
        CategoryFilter(
            categories = state.categories,
            selectedCategory = state.selectedCategory,
            onCategorySelected = { category ->
                viewModel.filterByCategory(category)
            }
        )

        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
            else -> {
                ProductList(
                    products = state.filteredProducts,
                    onClick = navigateToDetails,
                    onSelectionChange = { productId ->
                        viewModel.toggleProductSelection(productId)
                    },
                    loadMore = { viewModel.loadMoreProducts() }
                )
            }
        }
    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    ForSalesTheme {
        HomeScreen(navigateToDetails = {}, navigateToCart = {}, categoryId = 2)
    }
}