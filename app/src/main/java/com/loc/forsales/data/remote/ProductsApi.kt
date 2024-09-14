package com.loc.forsales.data.remote

import com.loc.forsales.data.remote.dto.ProductsResponse
import com.loc.forsales.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {
    @GET("product")
    suspend fun getProducts(): List<Product>
    @GET("product")
    suspend fun searchProducts(@Query("search") query :String): List<Product>

    @GET("product/{product_id}/detail")
    suspend fun getProduct(@Path("product_id") productID: Int):List<Product>
}