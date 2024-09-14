package com.loc.forsales.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("product_id")
    val productID: Int,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("product_category")
    val productCategory: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("img_url")
    val ImgUrl: String,
    @SerializedName("product_price")
    val productPrice: String,
    @SerializedName("last_updated")
    val lastUpdated: String,

    ): Serializable