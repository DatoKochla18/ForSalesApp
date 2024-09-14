package com.loc.forsales.presentation.detail

import com.loc.forsales.domain.model.Product

data class DetailState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)