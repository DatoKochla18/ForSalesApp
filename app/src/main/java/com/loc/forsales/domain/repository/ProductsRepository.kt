package com.loc.forsales.domain.repository

import com.loc.forsales.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProducts(): Flow<List<Product>>

    suspend fun getProduct(id: Int): Flow<List<Product>>
    suspend fun searchProduct(query:String): Flow<List<Product>>

}