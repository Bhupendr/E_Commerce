package com.Frndzcart.urbanchoice.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.databinding.OrderitemDetailsBinding
import com.Frndzcart.urbanchoice.model.OrderData
import com.pixplicity.easyprefs.library.Prefs

class OrderDetails : AppCompatActivity(){

    private lateinit var binding : OrderitemDetailsBinding
    var orderDataModel = OrderData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = OrderitemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle : Bundle? = intent.extras

        if (bundle != null) {
            orderDataModel = bundle.getParcelable(Global.ParticularOrder)!!

        }
        setData()
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setData() {
        binding.nameValue.text = Prefs.getString(Global.Username,"")
        binding.mobileValue.text = Prefs.getString(Global.mobilenumber,"")
        binding.emailValue.text = Prefs.getString(Global.email,"")
        binding.addressValue.text = orderDataModel.address
        binding.totalValue.text = orderDataModel.total_amount
        binding.dateValue.text = Global.splitdatefromstring(orderDataModel.created_at)
        binding.orderTimeValue.text = Global.splittimefromstring(orderDataModel.created_at)
        binding.paymentmethodValue.text = Prefs.getString(Global.Paymenttype,"")
    }
}