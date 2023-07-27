package com.sancho.kotlin_retrofit_fake_apppii.retrofit

import com.sancho.kotlin_retrofit_fake_apppii.model.ProductModelItem
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    //https://fakestoreapi.com/products

    @GET("products")
    fun getAllProducts():Call<ArrayList<ProductModelItem>>

}