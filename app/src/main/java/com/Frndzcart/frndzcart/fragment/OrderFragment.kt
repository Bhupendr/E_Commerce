package com.Frndzcart.frndzcart.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.frndzcart.Global.Global
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.activity.MainActivity
import com.Frndzcart.frndzcart.api.ApiClient
import com.Frndzcart.frndzcart.databinding.FragmentOrderBinding
import com.Frndzcart.frndzcart.model.LoginResponse
import com.Frndzcart.frndzcart.model.OrderData
import com.Frndzcart.frndzcart.model.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderFragment : Fragment() {

    lateinit var binding : FragmentOrderBinding
    lateinit var adapter: OrderAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    var arrayList = ArrayList<OrderData>()



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(layoutInflater)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        Global.classname = javaClass.name
        linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val cart : ImageView = activity?.findViewById(R.id.cart) as ImageView
        val search : SearchView = activity?.findViewById(R.id.search)!!
        search.isVisible = false
        cart.isVisible = false

        binding.progressBar.visibility = View.VISIBLE
        if(Global.checkInternet(context)){
            callApi()
        }
        return binding.root
    }

    private fun callApi() {
        val call = ApiClient().service.orderlist("admin/apis/customer-order.php?customer_id=" + Global.customerid)
        call.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
            ) {
                if(response.body() != null) {
                    // productList!!.value = response.body()?.data
                    binding.progressBar.visibility = View.GONE
                    arrayList.clear()
                    arrayList.addAll(response.body()!!.data)
                    linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = OrderAdapter(arrayList)
                    binding.productRecyclerView.layoutManager = linearLayoutManager
                    binding.productRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                Log.e("ResultMsz=?",response.body().toString())

            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("ErrorMsz==>", t.message.toString())
            }
        })
    }

}
