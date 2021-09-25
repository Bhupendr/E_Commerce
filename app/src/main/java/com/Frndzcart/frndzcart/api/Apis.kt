package com.Frndzcart.frndzcart.api


import com.Frndzcart.frndzcart.model.LoginResponse
import com.Frndzcart.frndzcart.model.ProductResponse
import com.Frndzcart.frndzcart.model.ProductResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface Apis {


    @GET("sales/apis/products.php")
    fun getProductList():Call<List<ProductResponseItem>>


/*    @POST("invent/update")
    fun updateproduct(@Body body: ProductDataModel):Call<ProductResponse>*/



    @GET
    fun login(@Url url:String): Call<LoginResponse>



    @GET
    fun order(@Url url:String): Call<String>

}