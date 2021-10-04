package com.Frndzcart.urbanchoice.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.api.ApiClient
import com.Frndzcart.urbanchoice.databinding.LoginPageBinding
import com.Frndzcart.urbanchoice.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInActivity : AppCompatActivity() {

    private lateinit var binding : LoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp1 = getSharedPreferences("Login", MODE_PRIVATE)

        if(sp1!=null){
            val unm = sp1.getString("Unm", null)
            val pass = sp1.getString("Psw", null)
            binding.username.setText(unm)
            binding.mobilenum.setText(pass)

        }


        binding.login.setOnClickListener{
            if(validation(binding.username.text, binding.mobilenum.text)){
                Global.phonenum = binding.mobilenum.text.toString()
                val sp = getSharedPreferences("Login", MODE_PRIVATE)
                val Ed = sp.edit()
                Ed.putString("Unm", binding.username.text.toString())
                Ed.putString("Psw", binding.mobilenum.text.toString())
                Ed.apply()
                LogIn(binding.username.text.toString(), binding.mobilenum.text.toString())
            }

           // startActivity(Intent(this,MainActivity::class.java))
        }



    }

    private fun validation(username: Editable?, mobile: Editable?): Boolean {
        if(username!!.isEmpty()){
            binding.username.error = resources.getString(R.string.enternameerror)
            return false
        }else  if(mobile!!.isEmpty()|| mobile.trim().length<8) {
            binding.mobilenum.error = resources.getString(R.string.enterphoneerror)
            return false
        }
        return true

    }

    private fun LogIn(username: String, mobile: String) {
        val call = ApiClient().service.login("admin/apis/login.php?phone=" + "${mobile}" + "&name=" + "${username}")
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.body() != null) {
                    // productList!!.value = response.body()?.data
                    Global.customerid = response.body()!!.id
                    Global.name = response.body()!!.name
//                        val intent
                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                }
                Log.e("ResultMsz=?", response.body().toString())

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("ErrorMsz==>", t.message.toString())
            }
        })
    }
}