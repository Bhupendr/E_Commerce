package com.Frndzcart.urbanchoice.activity

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.adapter.ParticularOrderAdapter
import com.Frndzcart.urbanchoice.api.ApiClient
import com.Frndzcart.urbanchoice.databinding.OrderitemDetailsBinding
import com.Frndzcart.urbanchoice.model.ItemDetail
import com.Frndzcart.urbanchoice.model.OrderData
import com.Frndzcart.urbanchoice.model.ParticularOrderResponse
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetails : AppCompatActivity(){

    private lateinit var binding : OrderitemDetailsBinding
    var orderDataModel = OrderData()
    lateinit var adapter: ParticularOrderAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var particularOrderResponse :ParticularOrderResponse
    var itemDetail = ArrayList<ItemDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = OrderitemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle : Bundle? = intent.extras

        if (bundle != null) {
            orderDataModel = bundle.getParcelable(Global.ParticularOrder)!!

        }

        if(Global.checkInternet(this)){
            binding.progressBar.isVisible= true
            callApi(orderDataModel.order_id)
        }else{
            Global.noInternetdialog(this)
        }


        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun callApi(orderId: String) {
        Log.e("url","admin/apis/order-one1.php?o=%22"+orderId+"%22")
        val call = ApiClient().service.particularorder("admin/apis/order-one1.php?o=%22"+orderId+"%22")
        call.enqueue(object : Callback<ParticularOrderResponse> {
            override fun onResponse(
                    call: Call<ParticularOrderResponse>,
                    response: Response<ParticularOrderResponse>
            ) {
                if(response.body() != null) {

                    particularOrderResponse = response.body()!!
                    itemDetail.addAll(particularOrderResponse.item_details)
                    linearLayoutManager = LinearLayoutManager(this@OrderDetails, LinearLayoutManager.VERTICAL, false)
                    adapter = ParticularOrderAdapter(itemDetail)
                    binding.productRecyclerView.layoutManager = linearLayoutManager
                    binding.productRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    setData(particularOrderResponse)

                }
            }

            override fun onFailure(call: Call<ParticularOrderResponse>, t: Throwable) {
                binding.progressBar.isVisible = false

            }
        })
    }

    private fun setData(particularOrderResponse: ParticularOrderResponse) {
        try {

            binding.nameValue.text = Prefs.getString(Global.Username, "")
            binding.mobileValue.text = Prefs.getString(Global.mobilenumber, "")
            binding.emailValue.text = Prefs.getString(Global.email, "")
            binding.addressValue.text = particularOrderResponse.address
            binding.totalValue.text = particularOrderResponse.total_amount
            binding.dateValue.text = Global.splitdatefromstring(particularOrderResponse.created_at)
            binding.orderTimeValue.text = Global.splittimefromstring(particularOrderResponse.created_at)
            binding.paymentmethodValue.text = Prefs.getString(Global.Paymenttype, "")
            binding.progressBar.isVisible = false
        }catch (e : Exception){
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }
    }
}