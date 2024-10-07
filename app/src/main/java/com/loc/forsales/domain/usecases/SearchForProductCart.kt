package com.loc.forsales.domain.usecases

import android.util.Log
import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
class SearchForProductCart @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(query: String,mainCategoryId:Int): Flow<List<List<Product>>> = flow {
        Log.d("SearchForProductCart", "Searching for products with query: $query")
        try {
            productsRepository.searchForProductCart(query,mainCategoryId).collect { products ->
                Log.d("SearchForProductCart", "Retrieved products: $products")
                emit(products)
            }
        } catch (e: Exception) {
            Log.e("SearchForProductCart", "Error searching for products", e)
            emit(emptyList())
        }
    }
}