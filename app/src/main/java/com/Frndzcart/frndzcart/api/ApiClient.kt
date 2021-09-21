package com.Frndzcart.frndzcart.api

import com.Frndzcart.frndzcart.Global.Global
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager


class ApiClient {


  private val retrofit: Retrofit
      get() = Retrofit.Builder()
                        .baseUrl(Global.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          /*.client(okHttpClient)*/
          .build()


    val service = retrofit.create(Apis::class.java)!!

    var certPinner = CertificatePinner.Builder()
        .add(
            "mdsys.in",
            "sha256/rp4H2p1GVJhFSvEKRSuwdeo4DMFzxYoJkFfKgfmpALw="
        )
        .build()

    var okHttpClient = OkHttpClient.Builder()
        .certificatePinner(certPinner)
        .build()







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
