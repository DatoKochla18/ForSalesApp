package com.loc.forsales.presentation.cart

import com.loc.forsales.domain.model.Product

data class ProductCartState(
    val productsCart: List<List<Product>> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)