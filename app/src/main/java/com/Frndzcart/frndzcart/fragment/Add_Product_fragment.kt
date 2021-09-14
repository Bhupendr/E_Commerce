package com.Frndzcart.frndzcart.fragment

import ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.frndzcart.model.Item_Model
import com.Frndzcart.frndzcart.R


class Add_Product_fragment : Fragment() {

    lateinit var adapter: ProductAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.add_product, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        val microphone : ImageView  = activity?.findViewById(R.id.microphone)!!
        val cart : ImageView  = activity?.findViewById(R.id.cart)!!
        val search : SearchView = activity?.findViewById(R.id.search)!!
        search.isVisible = true
        microphone.isVisible = true


        var heading : TextView = activity?.findViewById(R.id.heading) as TextView
        heading.setText("Add Product")

        setAdapter()
        return root
    }

    private fun setAdapter() {
        val  recyclerView : RecyclerView = root.findViewById(R.id.product_recycler_view) as RecyclerView
        var arrayList = arrayListOf<Item_Model>(
            Item_Model("Parle Bhujia", "Rs. 100", "Rs. 60", "In Stock", "Rs 40 save", "1 kg",1),
            Item_Model("Haldiram Bhujia", "Rs. 70", "Rs. 60", "In Stock", "Rs 10 save", "500 g",1),
            Item_Model(
                "Bikaner Aloo Bhujia",
                "No offer available",
                "Rs. 40",
                "Out of Stock",
                "",
                "250 g"
           ,1 ),
            Item_Model("Pooja Bhujia", "Rs. 30", "Rs. 20", "In Stock", "Rs 10 save", "250 g",1)

        )
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = ProductAdapter(arrayList)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }
}
