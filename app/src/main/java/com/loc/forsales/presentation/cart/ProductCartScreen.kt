package com.loc.forsales.presentation.cart

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.forsales.presentation.cart.components.ProductsCartTable

@Composable
fun ProductCartScreen(
    viewModel: ProductCartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        Log.d("ProductCartScreen", "State updated: $state")
    }

    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
    } else if (state.error != null) {
        Text(text = "Error: ${state.error}", color = Color.Red, modifier = Modifier.fillMaxSize().padding(16.dp))
    } else if (state.productsCart.isNotEmpty()) {
        ProductsCartTable(productsCart = state.productsCart)
    } else {
        Text("No products in cart", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
    }
}

