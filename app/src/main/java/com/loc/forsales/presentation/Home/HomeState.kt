package com.loc.forsales.presentation.Home

import com.loc.forsales.domain.model.Product

data class HomeState(
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "All",
    val isLoading: Boolean = true,
    val error: String? = null,
    val query: String = "", // Add this line
) {
    val visibleProducts: List<Product>
        get() = if (selectedCategory == "All") {
            filteredProducts
        } else {
            filteredProducts.filter { it.productCategory == selectedCategory }
        }
}