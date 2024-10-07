package com.loc.forsales.domain.usecases

import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke(mainCategoryId: Int, page: Int, pageSize: Int): Flow<List<Product>> {
        return productsRepository.getProducts(mainCategoryId, page, pageSize)
    }
}