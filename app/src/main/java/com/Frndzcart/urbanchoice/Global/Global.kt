package com.Frndzcart.urbanchoice.Global

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.Frndzcart.urbanchoice.model.Item_Model
import com.Frndzcart.urbanchoice.model.ProductResponseItem


object Global {

//    const val BASE_URL = "https://www.mdsys.in/"
    const val BASE_URL = "https://urbanchoice.co/"
    const val BASE_Image_URL ="http://urbanchoice.co/"
     var customerid = "0"
     var phonenum = "0"
     var name = "0"
    var pricing : Double = 0.00
    var classname =""

    const val Username = "_Username"
    const val mobilenumber = "_mobilenumber"
    const val email = "_email"
    const val address = "_address"
    const val userID = "_userID"
    const val autoLogin = "_autoLogin"


    fun hideKeybaord(v: View, context: Context) {
        val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
        v.clearFocus()
    }



        var cartList:MutableList<ProductResponseItem?> =  ArrayList()

    fun checkInternet(context: Context?): Boolean {

        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        (return connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED)
    }


}