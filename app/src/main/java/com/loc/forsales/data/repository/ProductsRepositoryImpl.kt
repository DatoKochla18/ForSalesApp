package com.loc.forsales.data.repository

import android.util.Log
import com.loc.forsales.data.remote.ProductsApi
import com.loc.forsales.domain.model.MainCategory
import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepositoryImpl(private val productsApi: ProductsApi) : ProductsRepository {

    override fun getProducts(mainCategoryId: Int, page: Int, pageSize: Int): Flow<List<Product>> {
        return flow {
            try {
                val response = productsApi.getProducts(mainCategoryId, page, pageSize)
                emit(response.results)
            } catch (e: Exception) {
                emit(emptyList()) // Emit an empty list in case of an error
            }
        }
    }

    override fun getMainCategories(): Flow<List<MainCategory>> {
        return flow {
            try {
                val mainCategories = productsApi.getMainCategories()
                emit(mainCategories)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }

    override suspend fun getProduct(id: Int): Flow<List<Product>> {
        return flow {
            try {
                val products = productsApi.getProduct(productID = id)
                emit(products)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }

    override suspend fun searchProduct(mainCategoryId: Int, page: Int, pageSize: Int,query: String): Flow<List<Product>> {
        return flow {
            try {
                val products = productsApi.searchProducts(query = query, mainCategoryID = mainCategoryId, page = page, pageSize = pageSize)
                emit(products.results)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }

    override suspend fun searchForProductCart(query: String,mainCategoryId: Int): Flow<List<List<Product>>> = flow {
        Log.d("ProductsRepositoryImpl", "Searching for products in cart with query: $query")
        try {
            val response = productsApi.searchForProductsCart(query = query,mainCategoryID=mainCategoryId)
            Log.d("ProductsRepositoryImpl", "Retrieved products: $response")
            emit(response)
        } catch (e: Exception) {
            Log.e("ProductsRepositoryImpl", "Error searching for products in cart", e)
            emit(emptyList())
        }
    }
}