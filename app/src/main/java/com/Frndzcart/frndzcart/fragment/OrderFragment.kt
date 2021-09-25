package com.Frndzcart.frndzcart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.Frndzcart.frndzcart.Global.Global
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    lateinit var binding : FragmentOrderBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(layoutInflater)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val cart : ImageView = activity?.findViewById(R.id.cart) as ImageView
        val search : SearchView = activity?.findViewById(R.id.search)!!
        search.isVisible = false
        cart.isVisible = false

        if(Global.checkInternet(context)){
            callApi()
        }
        return binding.root
    }

    private fun callApi() {

    }

}
