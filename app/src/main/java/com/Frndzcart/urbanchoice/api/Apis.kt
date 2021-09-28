package com.Frndzcart.urbanchoice.api


import com.Frndzcart.urbanchoice.model.LoginResponse
import com.Frndzcart.urbanchoice.model.OrderResponse
import com.Frndzcart.urbanchoice.model.ProductResponse
import com.Frndzcart.urbanchoice.model.ProductResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface Apis {


    @GET("admin/apis/products.php")
    fun getProductList():Call<ProductResponse>


    @GET
    fun orderlist(@Url url:String):Call<OrderResponse>



    @GET
    fun login(@Url url:String): Call<LoginResponse>



    @GET
    fun order(@Url url:String): Call<String>

}