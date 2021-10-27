package com.Frndzcart.urbanchoice.model

import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Frndzcart.urbanchoice.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {


    var productList: MutableLiveData<List<ProductResponseItem>>? = null


    //we will call this method to get the data
    fun getProductList(progressBar: ProgressBar): LiveData<List<ProductResponseItem>>? {
        //if the list is null
        productList = MutableLiveData<List<ProductResponseItem>>()
        //we will load it asynchronously from server in this method
        loadOpportunies(progressBar)


        //finally we will return the list
        return productList
    }

    private fun loadOpportunies(progressBar: ProgressBar) {
        val call = ApiClient().service.getProductList()
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                progressBar.isVisible = false
                if(response.body() != null) {
                    productList!!.value = response.body()?.data
                }

                Log.e("data", response.body().toString())

            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("fail", t.message.toString())
                progressBar.isVisible = false
            }
        })
    }

}