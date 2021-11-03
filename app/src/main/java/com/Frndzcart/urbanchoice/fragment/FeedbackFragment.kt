package com.Frndzcart.urbanchoice.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.activity.MainActivity
import com.Frndzcart.urbanchoice.api.ApiClient
import com.Frndzcart.urbanchoice.databinding.FeedbackFormBinding
import com.Frndzcart.urbanchoice.model.FeedbackResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeedbackFragment : Fragment() {


    private lateinit var binding : FeedbackFormBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FeedbackFormBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        Global.classname = javaClass.name
//        (activity as DrawerLock?)!!.setDrawerLocked(true)


        val microphone : ImageView  = activity?.findViewById(R.id.microphone)!!
        val phone : ImageView  = activity?.findViewById(R.id.cart)!!
        val search : SearchView = activity?.findViewById(R.id.search)!!
        phone.isVisible = false
      /*  search.isVisible = true
        microphone.isVisible = true*/
        /*val heading : TextView = activity?.findViewById(R.id.heading) as TextView
        heading.text = "Add Product"*/

        binding.proceed.setOnClickListener {
            if(validation(binding.topicValue,binding.description,binding.rating.rating)){
                val feedbackresponse = FeedbackResponse(
                        customer_id = Global.customerid.toInt(),
                        title = binding.topicValue.text.toString(),
                        description = binding.description.text.toString(),
                        rating = binding.rating.rating.toString()
                )
                val gson = Gson()
                val jsonTut: String = gson.toJson(feedbackresponse)
                Log.e("json",jsonTut)

                callApi(jsonTut)
            }

        }
        return binding.root
    }

    private fun callApi(jsonTut: String) {
        val call = ApiClient().service.feedback("/admin/apis/feedback.php?feedback=" +jsonTut)
        call.enqueue(object : Callback<String> {
            override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
            ) {
                if(response.body() != null) {

                    Log.e("ResultMsz=?",response.body().toString())
                    Toast.makeText(context,"Thank you!! Your response has been recorded",Toast.LENGTH_LONG).show()

                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("ErrorMsz==>", t.message.toString())
            }
        })
    }

    private fun validation(topic: TextInputEditText, description: TextInputEditText, numStars: Float): Boolean {
        if(topic.text!!.isEmpty()){
            topic.error = "Enter topic"
            return false
        }
        if(description.text!!.isEmpty()){
            topic.error = "Enter description"
            return false
        }
        if(numStars == 0.0f){
            Toast.makeText(context,"Please give us rating",Toast.LENGTH_LONG).show()
            return false
        }
        return true

    }
    
}
