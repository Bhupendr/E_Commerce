package com.Frndzcart.frndzcart.model

import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Frndzcart.frndzcart.api.ApiClient
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
        call.enqueue(object : Callback<List<ProductResponseItem>> {
            override fun onResponse(
                call: Call<List<ProductResponseItem>>,
                response: Response<List<ProductResponseItem>>
            ) {
                progressBar.isVisible = false
                if(response.body() != null) {
                    productList!!.value = response.body()
                }
                Log.e("ok",response.message())
                Log.e("data", response.body().toString())

            }

            override fun onFailure(call: Call<List<ProductResponseItem>>, t: Throwable) {
                Log.e("fail", t.message.toString())
                progressBar.isVisible = false
            }
        })
    }

}