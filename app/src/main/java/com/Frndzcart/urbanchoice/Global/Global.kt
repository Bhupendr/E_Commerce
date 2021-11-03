package com.Frndzcart.urbanchoice.Global

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.model.ProductResponseItem
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object Global {




    //    const val BASE_URL = "https://www.mdsys.in/"
    const val BASE_URL = "https://urbanchoice.co/"
    const val BASE_Image_URL ="http://urbanchoice.co/"
     var customerid = "0"

    var pricing : Double = 0.00
    var classname =""

    const val AppIntro = "_AppIntro"
    const val Username = "_Username"
    const val mobilenumber = "_mobilenumber"
    const val email = "_email"
    const val address = "_address"
    const val userID = "_userID"
    const val autoLogin = "_autoLogin"
    const val ParticularOrder = "_ParticularOrder"
    const val Paymenttype = "_Paymenttype"


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

    fun splitdatefromstring(datetime: String):String{
        try {
            val f: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val d: Date = f.parse(datetime)
            val date: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            return date.format(d)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return "No date found"
    }


    fun splittimefromstring(datetime: String):String{
        try {
            val f: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val d: Date = f.parse(datetime)
            val time: DateFormat = SimpleDateFormat("hh:mm:ss a")
            return time.format(d)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return "No time found"
    }


    fun noInternetdialog(context: Context)
    {
//        val dialogbinding = DeliveryDetailsBinding.inflate(layoutInflater)
        val dialog = Dialog(context, R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.no_internet_connection)
        dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
        )
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.gravity = Gravity.CENTER
        val try_again : Button = dialog.findViewById(R.id.try_again)

        try_again.setOnClickListener {
            if(checkInternet(context)){
                dialog.dismiss()
            }else{
                Toast.makeText(context,context.resources.getString(R.string.check_internet), Toast.LENGTH_LONG).show()

            }

        }


        dialog.window!!.attributes = lp
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

}