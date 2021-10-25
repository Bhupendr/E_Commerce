package com.Frndzcart.urbanchoice.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.api.ApiClient
import com.Frndzcart.urbanchoice.databinding.LoginPageBinding
import com.Frndzcart.urbanchoice.model.LoginResponse
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInActivity : AppCompatActivity() {

    private lateinit var binding : LoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.login.setOnClickListener{
            if(validation(binding.username.text, binding.mobilenum.text)){
                binding.login.startLoading()
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

    fun LogIn(username: String, mobile: String) {
        val call = ApiClient().service.login("admin/apis/login.php?phone=" + "${mobile}" + "&name=" + "${username}")
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.body() != null) {
                    // productList!!.value = response.body()?.data
                    binding.login.loadingSuccessful()
                    Global.customerid = response.body()!!.id


                    Prefs.putString(Global.Username, response.body()!!.name)
                    Prefs.putString(Global.mobilenumber, response.body()!!.phone)
                    Prefs.putString(Global.userID, response.body()!!.id)
                    Prefs.putBoolean(Global.autoLogin,true)
//                        val intent

                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))

//                    finish()
                }else{
                    binding.login.loadingFailed()
                }
                Log.e("ResultMsz=?", response.body().toString())

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("ErrorMsz==>", t.message.toString())
            }
        })
    }
}