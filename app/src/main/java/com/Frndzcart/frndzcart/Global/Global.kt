package com.Frndzcart.frndzcart.Global

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.Frndzcart.frndzcart.model.Item_Model


object Global {

    const val BASE_URL = "https://www.mdsys.in/"


    fun hideKeybaord(v: View, context: Context) {
        val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
        v.clearFocus()
    }



        var cartList:MutableList<Item_Model?> =  ArrayList()




}