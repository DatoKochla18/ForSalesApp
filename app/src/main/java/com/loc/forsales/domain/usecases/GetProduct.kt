package com.loc.forsales.domain.usecases

import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class GetProduct @Inject constructor(
    private val productsRepository: ProductsRepository,
) {
    // `invoke` is now a suspend function, which can be called inside a coroutine
    suspend operator fun invoke(id: Int): Flow<List<Product>> {
        return productsRepository.getProduct(id = id)
    }
}

