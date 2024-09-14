package com.loc.forsales.data.repository

import com.loc.forsales.data.remote.ProductsApi
import com.loc.forsales.data.remote.dto.toProductList
import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepositoryImpl(private val productsApi: ProductsApi) : ProductsRepository {

    override fun getProducts(): Flow<List<Product>> {
        return flow {
            try {
                val products = productsApi.getProducts()
                emit(products)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }


        override suspend fun getProduct(id: Int): Flow<List<Product>> {
            return flow {
                try {
                    // Fetch the list of products from the API using the provided product ID
                    val products = productsApi.getProduct(productID = id)
                    emit(products)
                } catch (e: Exception) {
                    // Log the exception or handle it accordingly
                    emit(emptyList())
                }
            }
        }

    override suspend fun searchProduct(query: String): Flow<List<Product>> {
        return flow {
            try {
                val products = productsApi.searchProducts(query=query)
                emit(products)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }

}
