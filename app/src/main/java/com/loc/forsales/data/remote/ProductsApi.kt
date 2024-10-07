package com.loc.forsales.data.remote

import com.loc.forsales.data.remote.dto.ProductsResponse
import com.loc.forsales.domain.model.MainCategory
import com.loc.forsales.domain.model.Product
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {
    @GET("product/{mainCategoryID}")
    suspend fun getProducts(@Path("mainCategoryID") mainCategoryID: Int, @Query("page") page: Int = 1,@Query("page_size") pageSize: Int = 10): ProductsResponse<Product>
    @GET("product/{mainCategoryID}")
    suspend fun searchProducts(@Path("mainCategoryID") mainCategoryID: Int, @Query("page") page: Int = 1,@Query("page_size") pageSize: Int = 10,@Query("search") query :String): ProductsResponse<Product>
    @GET("product/cart/{mainCategoryID}")
    suspend fun searchForProductsCart(@Path("mainCategoryID") mainCategoryID: Int,@Query("ids") query :String,): List<List<Product>>

    @GET("product/mainCategory")
    suspend fun getMainCategories(): List<MainCategory>

    @GET("product/{product_id}/detail")
    suspend fun getProduct(@Path("product_id") productID: Int):List<Product>
}