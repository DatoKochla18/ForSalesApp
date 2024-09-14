package com.loc.forsales.data.remote.dto

import com.loc.forsales.domain.model.Product

data class ProductsResponse(
    val products: List<Product>
)


// Extension function to map ProductsResponse to List<Product>
fun ProductsResponse.toProductList(): List<Product> {
    return products
}