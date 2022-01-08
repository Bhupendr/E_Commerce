package com.Frndzcart.urbanchoice.fragment

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
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.adapter.OrderAdapter
import com.Frndzcart.urbanchoice.api.ApiClient
import com.Frndzcart.urbanchoice.databinding.FragmentOrderBinding
import com.Frndzcart.urbanchoice.model.OrderData
import com.Frndzcart.urbanchoice.model.OrderResponse
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
        }else{
            binding.progressBar.visibility = View.GONE
//            Toast.makeText(context,resources.getString(R.string.check_internet), Toast.LENGTH_LONG).show()
            Global.noInternetdialog(requireContext())
        }
        return binding.root
    }

    private fun callApi() {
        Log.e("url","admin/apis/customer-order.php?customer_id=" + Global.customerid)
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
