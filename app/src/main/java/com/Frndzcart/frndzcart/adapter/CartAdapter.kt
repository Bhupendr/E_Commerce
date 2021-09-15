package com.Frndzcart.frndzcart.adapter

import android.content.Context
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.frndzcart.Global.Global
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.databinding.ProductItemViewBinding
import com.Frndzcart.frndzcart.fragment.Cartfragment
import com.Frndzcart.frndzcart.`interface`.counter
import com.Frndzcart.frndzcart.`interface`.setvisibility
import kotlinx.android.synthetic.main.product_item_view.view.*

class CartAdapter() : RecyclerView.Adapter<CartAdapter.Category_Holder>()  {

    private var context: Context? = null
    lateinit var vibe : Vibrator
    lateinit var count : counter
    lateinit var visibiltu : setvisibility

     constructor(contxt: Cartfragment) : this() {
        this.visibiltu = contxt
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Category_Holder {

        val binding = ProductItemViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent.context
        count = parent.context as counter
        vibe = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        return Category_Holder(binding)
    }

    override fun getItemCount(): Int {
        return Global.cartList.size

    }


    override fun onBindViewHolder(holder: Category_Holder, position: Int) {
        with(holder){
            var listdata = Global.cartList[position]

                itemView.add_quantity.isVisible = true
                itemView.add_new_item.isVisible = false
                itemView.item_name.text = listdata?.item_name
                itemView.item_price.text = listdata?.item_price
                itemView.discounted_price.text = listdata?.discount_price
                itemView.offer_percent.text = listdata!!.off
                if(listdata.off.isEmpty())
                    itemView.red_offer_img.isVisible = false
                itemView.weight.text = listdata.weght

                itemView.total.text = listdata.item_quantity.toString()
                itemView.add.setOnClickListener(View.OnClickListener {
                    listdata.item_quantity = 1
                    vibe.vibrate(80)
                    itemView.add_quantity.isVisible = true
                    itemView.add_new_item.isVisible = false
                    itemView.total.text = listdata.item_quantity.toString()

                })

                itemView.plus.setOnClickListener(View.OnClickListener {
                    listdata.item_quantity++
                    itemView.total.text = listdata.item_quantity.toString()

                })

                itemView.minus.setOnClickListener(View.OnClickListener {

                    if(listdata.item_quantity>1){
                        listdata.item_quantity--
                        itemView.total.text = listdata.item_quantity.toString()
                    }else{
                        itemView.add_quantity.isVisible = false
                        itemView.add_new_item.isVisible = true
                        Global.cartList.remove(listdata)
                    }
                    count.onCount(Global.cartList.size)
                    visibiltu.setVisibilty(Global.cartList.size)
                    notifyDataSetChanged()
                })

        }






    }
   inner class Category_Holder(val itemView:  ProductItemViewBinding) : RecyclerView.ViewHolder(itemView.root)

}


