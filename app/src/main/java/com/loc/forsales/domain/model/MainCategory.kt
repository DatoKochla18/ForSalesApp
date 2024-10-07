package com.loc.forsales.domain.model

import com.google.gson.annotations.SerializedName

data class MainCategory (
    val id:Int,
    @SerializedName("main_category")
    val name:String,
    @SerializedName("category_url")
    val imgUrl:String
)