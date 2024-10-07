package com.loc.forsales.domain.repository

import com.loc.forsales.domain.model.MainCategory
import com.loc.forsales.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProducts(mainCategoryId: Int, page: Int, pageSize: Int): Flow<List<Product>>
    fun getMainCategories(): Flow<List<MainCategory>>

    suspend fun getProduct(id: Int): Flow<List<Product>>
    suspend fun searchProduct(mainCategoryId: Int, page: Int, pageSize: Int,query:String): Flow<List<Product>>

    suspend fun searchForProductCart(query:String,mainCategoryId: Int): Flow<List<List<Product>>>

}