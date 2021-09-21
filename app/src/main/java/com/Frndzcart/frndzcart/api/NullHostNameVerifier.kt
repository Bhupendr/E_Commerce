package com.Frndzcart.frndzcart.api

import android.util.Log
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

open class NullHostNameVerifier : HostnameVerifier {

    override fun verify(hostname: String, session: SSLSession?): Boolean {
        Log.i("RestUtilImpl", "Approving certificate for $hostname")
        return true
    }

}