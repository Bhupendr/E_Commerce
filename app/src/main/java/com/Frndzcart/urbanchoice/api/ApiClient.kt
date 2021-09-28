package com.Frndzcart.urbanchoice.api

import com.Frndzcart.urbanchoice.Global.Global
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*


class ApiClient {


  private val retrofit: Retrofit
      get() = Retrofit.Builder()
        .baseUrl(Global.BASE_URL).client(getUnsafeOkHttpClient()?.build())
          .addConverterFactory(GsonConverterFactory.create(gson))
          /*.client(okHttpClient)*/
          .build()

    var gson = GsonBuilder()
            .setLenient()
            .create()




    val service = retrofit.create(Apis::class.java)!!
  /*  var certPinner = CertificatePinner.Builder()
    .add(
   "mdsys.in",
   "sha256/rp4H2p1GVJhFSvEKRSuwdeo4DMFzxYoJkFfKgfmpALw=")
    .build()

    var okHttpClient = OkHttpClient.Builder()
    .certificatePinner(certPinner)
    .build()*/







   /* companion object {
       const val BASE_URL = "http://103.234.187.86:8000/"
        fun create(): Apis {
        val retrofit = Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(BASE_URL)
         .build()
         return retrofit.create(Apis::class.java)

        }
    }*/


    fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
