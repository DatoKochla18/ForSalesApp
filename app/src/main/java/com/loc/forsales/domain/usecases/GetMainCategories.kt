package com.loc.forsales.domain.usecases

import com.loc.forsales.domain.model.MainCategory
import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMainCategories @Inject constructor(
    private val mainCategoryRepository: ProductsRepository
) {
    operator fun invoke(): Flow<List<MainCategory>> {
        return mainCategoryRepository.getMainCategories()
    }
}