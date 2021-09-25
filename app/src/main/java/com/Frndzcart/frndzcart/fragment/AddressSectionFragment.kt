package com.Frndzcart.frndzcart.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.Frndzcart.frndzcart.Global.Global
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.activity.MainActivity
import com.Frndzcart.frndzcart.api.ApiClient
import com.Frndzcart.frndzcart.databinding.AddressSectionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressSectionFragment : Fragment() {

    lateinit var binding: AddressSectionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddressSectionBinding.inflate(layoutInflater)
        setData()


binding.back.setOnClickListener {
    activity?.onBackPressed()
}

        binding.proceed.setOnClickListener{
            if(validation(binding.nameValue.text,binding.phoneValue.text,binding.emailValue.text,binding.address.text))
            {
                if(Global.checkInternet(context)){
                    val productidlist = Global.cartList.joinToString { "${it?.id}" }
                  //  val productid = productidlist.replace("\\s".toRegex(), "")
                    callApi(binding.address.text,productidlist)
                }
            }
        }

        return binding.root
    }

    private fun setData() {
        binding.totalcost.text = Global.pricing.toString()
        binding.nameValue.setText(Global.name)
        binding.phoneValue.setText(Global.phonenum)
    }

    private fun callApi(address: Editable?, productidlist: String) {
        val call = ApiClient().service.order("sales/apis/order.php?o=%7B\"customer_id\":"+ Global.customerid +"," +
                "\"total_amount\":"+ Global.pricing.toString() +",\"items\"" +
                ":\""+ productidlist +"\",\"address\":\"" + address + "\"%7D")
        call.enqueue(object : Callback<String> {
            override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
            ) {
                if(response.body() != null) {
                    // productList!!.value = response.body()?.data
                Toast.makeText(context,"Order successfully",Toast.LENGTH_LONG).show()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    Global.cartList.clear()
//                        val intent

                }
                Log.e("ResultMsz=?",response.body().toString())

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("ErrorMsz==>", t.message.toString())
            }
        })
    }

    private fun validation(name: Editable?, phone: Editable?, email: Editable?, address: Editable?): Boolean {
        if(name!!.isEmpty()){
            binding.nameValue.error = resources.getString(R.string.enternameerror)
            return false
        }else  if(phone!!.isEmpty()|| phone.trim().length<8){
            binding.phoneValue.error = resources.getString(R.string.enterphoneerror)
            return false
        }else  if(email!!.isEmpty()|| isValidEmail(email.toString().trim())){
            binding.emailValue.error = resources.getString(R.string.enteremailerror)
            return false
        }else if(address!!.isEmpty()){
            binding.address.error = resources.getString(R.string.enteraddresserror)
            return false
        }
        return true
    }
    fun isValidEmail(target: CharSequence): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
