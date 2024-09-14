package com.loc.forsales.domain.usecases

import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProducts @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend  operator fun invoke(query:String): Flow<List<Product>> {
        return productsRepository.searchProduct(query)
    }
}