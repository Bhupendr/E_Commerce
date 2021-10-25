package com.Frndzcart.urbanchoice.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.activity.MainActivity
import com.Frndzcart.urbanchoice.api.ApiClient
import com.Frndzcart.urbanchoice.databinding.AddressSectionBinding
import com.nandroidex.upipayments.listener.PaymentStatusListener
import com.nandroidex.upipayments.models.TransactionDetails
import com.nandroidex.upipayments.utils.UPIPayment
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressSectionFragment : Fragment(), PaymentStatusListener {

    lateinit var binding: AddressSectionBinding
    lateinit var upiPayment: UPIPayment
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

        binding.radiogroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val cash: RadioButton = binding.root.findViewById(R.id.cash)
                val upi: RadioButton = binding.root.findViewById(R.id.upi)
                if (cash.isChecked) {
                    Prefs.putString(Global.Paymenttype,"Cash")

                } else if (upi.isChecked) {
                    Prefs.putString(Global.Paymenttype,"UPI")
                    integratepaymentmethod()
                }


            })

        binding.proceed.setOnClickListener{
            if(validation(binding.nameValue.text,binding.phoneValue.text,binding.emailValue.text,
                    binding.address.text, binding.radiogroup.checkedRadioButtonId))
            {
                Prefs.putString(Global.email,binding.emailValue.text.toString())
                Prefs.putString(Global.Username,binding.nameValue.text.toString())
                Prefs.putString(Global.address,binding.address.text.toString())
                if(Global.checkInternet(context)){
                    val productidlist = Global.cartList.joinToString { "${it?.id}" }

                  //  val productid = productidlist.replace("\\s".toRegex(), "")
                    callApi(binding.address.text,setOrder())
                }
            }
        }

        return binding.root
    }

    private fun integratepaymentmethod() {


        fun getRandomString(length: Int) : String {
            val allowedChars = ('A'..'Z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }



        upiPayment  = UPIPayment.Builder()
            .with(requireActivity())
            .setPayeeVpa("ps97585412071@ybl")
            .setPayeeName("Urbanchoice")
            .setPayeeMerchantCode(getRandomString(10))
            .setTransactionId(getRandomString(10))
            .setTransactionRefId(getRandomString(10))
            .setDescription("Raw Meat")
            .setAmount(Global.pricing.toString())
            .build()

        upiPayment.setPaymentStatusListener(this)

        if (upiPayment.isDefaultAppExist()) {
            onAppNotFound()
            return
        }

        upiPayment.startPayment()
    }

    private fun setOrder():String{
    var result = ""
    for(i in 0 until Global.cartList.size){
        if(i==0){
            result=Global.cartList.get(i)!!.id + ":" + Global.cartList.get(i)!!.quantity
        }else{
            result=result + "," +Global.cartList.get(i)!!.id + ":" + Global.cartList.get(i)!!.quantity
        }

    }
    return result
}

    private fun setData() {
        binding.totalcost.text = Global.pricing.toString()
        binding.nameValue.setText(Prefs.getString(Global.Username,""))
        binding.phoneValue.setText(Prefs.getString(Global.mobilenumber,""))
        binding.emailValue.setText(Prefs.getString(Global.email,""))
        binding.address.setText(Prefs.getString(Global.address,""))
    }

    private fun callApi(address: Editable?, productidlist: String) {

        val call = ApiClient().service.order("admin/apis/order.php?o=%7B\"customer_id\":"+ Global.customerid +"," +
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

    private fun validation(
        name: Editable?,
        phone: Editable?,
        email: Editable?,
        address: Editable?,
        checkedRadioButtonId: Int
    ): Boolean {
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
        }else if (checkedRadioButtonId == -1){
            Toast.makeText(context, resources.getString(R.string.select_payment), Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }


    fun isValidEmail(target: CharSequence): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails?) {
        Log.d("TransactionDetails", transactionDetails.toString())
        upiPayment.detachListener()
    }

    override fun onTransactionSuccess() {
        Toast.makeText(context, "Success, Order Success", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Global.cartList.clear()

    }

    override fun onTransactionSubmitted() {
        Toast.makeText(context, "Pending | Submitted", Toast.LENGTH_SHORT).show()
    }

    override fun onTransactionFailed() {
        binding.upi.isChecked = false
        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onTransactionCancelled() {
        binding.upi.isChecked = false
        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
    }

    override fun onAppNotFound() {
        binding.upi.isChecked = false
        Toast.makeText(context, "App Not Found", Toast.LENGTH_SHORT).show()
    }
}
