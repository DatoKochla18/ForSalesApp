package com.loc.forsales.data.remote.dto

import com.loc.forsales.domain.model.Product
import org.w3c.dom.Text

data class ProductsResponse<T>(
    val count: Int,
    val next: String?,  // Nullable, as it will be null on the last page
    val previous: String?,  // Nullable
    val results: List<T>  // The actual list of products
)


