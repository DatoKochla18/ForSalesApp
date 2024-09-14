package com.loc.forsales.di

import com.loc.forsales.data.remote.ProductsApi
import com.loc.forsales.data.repository.ProductsRepositoryImpl
import com.loc.forsales.domain.repository.ProductsRepository
import com.loc.forsales.domain.usecases.GetProducts
import com.loc.forsales.util.Constants.BASE_URL


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        productsApi: ProductsApi
    ): ProductsRepository {
        return ProductsRepositoryImpl(productsApi)
    }


    @Provides
    @Singleton
    fun provideApiInstance(): ProductsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsApi::class.java)
    }


}