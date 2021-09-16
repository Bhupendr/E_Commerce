package com.Frndzcart.frndzcart.api

import com.Frndzcart.frndzcart.Global.Global
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ApiClient {


  private val retrofit = Retrofit.Builder()
                    .baseUrl(Global.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    val service = retrofit.create(Apis::class.java)!!

  /*  companion object {
       const val BASE_URL = "http://103.234.187.86:8000/"
        fun create(): Apis {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(Apis::class.java)

        }
    }*/


}
