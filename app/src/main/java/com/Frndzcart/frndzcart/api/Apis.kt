package com.Frndzcart.frndzcart.api


import com.Frndzcart.frndzcart.model.ProductResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Apis {


    @GET("sales/apis/products.php")
    fun getProductList():Call<ProductResponse>


}