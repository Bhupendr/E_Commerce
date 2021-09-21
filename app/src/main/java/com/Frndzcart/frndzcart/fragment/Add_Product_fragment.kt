package com.Frndzcart.frndzcart.fragment

import ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.`interface`.DrawerLock
import com.Frndzcart.frndzcart.model.ProductResponseItem
import com.Frndzcart.frndzcart.model.ProductViewModel


class Add_Product_fragment : Fragment() {

    lateinit var adapter: ProductAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var root: View
    private lateinit var  recyclerView : RecyclerView
    val arrayList = ArrayList<ProductResponseItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_product, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as DrawerLock?)!!.setDrawerLocked(true)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar?.setNavigationOnClickListener{
            activity?.onBackPressed()
        }
        recyclerView  = root.findViewById(R.id.product_recycler_view)
        val microphone : ImageView  = activity?.findViewById(R.id.microphone)!!
        val search : SearchView = activity?.findViewById(R.id.search)!!
        search.isVisible = true
        microphone.isVisible = true
        var heading : TextView = activity?.findViewById(R.id.heading) as TextView
        heading.setText("Add Product")
        callApi()

        return root
    }



    private fun callApi() {

        val model: ProductViewModel =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        model.getProductList()!!.observe(
            viewLifecycleOwner,
            {

                arrayList.clear()
                arrayList.addAll(it)
                recyclerView.layoutManager = linearLayoutManager
                adapter = ProductAdapter(arrayList)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            })
    }
}
