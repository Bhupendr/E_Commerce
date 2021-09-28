package com.Frndzcart.urbanchoice.fragment


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
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.`interface`.DrawerLock
import com.Frndzcart.urbanchoice.adapter.ProductAdapter
import com.Frndzcart.urbanchoice.model.ProductResponseItem
import com.Frndzcart.urbanchoice.model.ProductViewModel


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
        Global.classname = javaClass.name
//        (activity as DrawerLock?)!!.setDrawerLocked(true)
        /*val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar?.setNavigationOnClickListener{
            activity?.onBackPressed()
        }*/
        recyclerView  = root.findViewById(R.id.product_recycler_view)
        linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val microphone : ImageView  = activity?.findViewById(R.id.microphone)!!
        val search : SearchView = activity?.findViewById(R.id.search)!!
        val progressBar : ProgressBar = root.findViewById(R.id.progressBar)
        val cart : ImageView  = activity?.findViewById(R.id.cart)!!
        cart.isVisible = true
        cart.setBackgroundResource(R.drawable.ic_shopping_cart)
      /*  search.isVisible = true
        microphone.isVisible = true*/
        /*val heading : TextView = activity?.findViewById(R.id.heading) as TextView
        heading.text = "Add Product"*/

        cart.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, Cartfragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        progressBar.isVisible = true
        if(Global.checkInternet(context)){
            callApi(progressBar)
        }

        return root
    }



    private fun callApi(progressBar: ProgressBar) {

        val model: ProductViewModel =
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        model.getProductList(progressBar)!!.observe(
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
