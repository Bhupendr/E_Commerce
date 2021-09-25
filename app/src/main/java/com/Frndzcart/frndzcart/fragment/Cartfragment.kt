package com.Frndzcart.frndzcart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.Frndzcart.frndzcart.Global.Global
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.adapter.CartAdapter
import com.Frndzcart.frndzcart.databinding.CartBinding
import com.Frndzcart.frndzcart.`interface`.setvisibility
import com.Frndzcart.frndzcart.activity.MainActivity


class Cartfragment : Fragment(), setvisibility,View.OnClickListener {
    lateinit var adapter: CartAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var binding: CartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CartBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        setvisibil(Global.cartList.size)
        (activity as MainActivity).binding.search.isVisible = false
        (activity as MainActivity).binding.microphone.isVisible = false
        binding.add.setOnClickListener(this)
        binding.itemEmptyLayout.addProduct.setOnClickListener(this)
        binding.back.setOnClickListener(this)
        binding.itemEmptyLayout.backPress.setOnClickListener(this)
        binding.proceed.setOnClickListener(this)

        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = CartAdapter(this, binding.totalprice)
        binding.productRecyclerView.layoutManager = linearLayoutManager
        binding.productRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun setvisibil(size: Int) {
        if(size==0){
            binding.itemAddLayout.visibility = View.GONE
            binding.itemEmptyLayout.root.visibility = View.VISIBLE
        }else{
            binding.itemAddLayout.visibility = View.VISIBLE
            binding.itemEmptyLayout.root.visibility = View.GONE

        }
    }

    override fun setVisibilty(size: Int) {
        setvisibil(size)
    }

    override fun onClick(v: View?) {
        when(v?.id){
           R.id.add_product,R.id.add ->{
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.container, Add_Product_fragment())
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
            R.id.back,
            R.id.back_press -> {activity?.onBackPressed()}
            R.id.proceed -> {


                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.container, AddressSectionFragment())
                transaction?.addToBackStack(null)
                transaction?.commit()
            }

        }
    }


}
