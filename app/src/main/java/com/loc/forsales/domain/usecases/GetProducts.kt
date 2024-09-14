package com.loc.forsales.domain.usecases

import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return productsRepository.getProducts()
    }
}