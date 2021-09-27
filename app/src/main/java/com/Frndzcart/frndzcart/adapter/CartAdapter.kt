package com.Frndzcart.frndzcart.adapter

import android.content.Context
import android.graphics.Paint
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    lateinit var textView: TextView

     constructor(contxt: Cartfragment, totalprice: TextView) : this() {
        this.visibiltu = contxt
        this.textView = totalprice
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
                itemView.item_name.text = listdata?.title
                itemView.item_price.text = listdata?.mrp
            Global.pricing = Global.cartList.sumBy {( it!!.quantity * it.price.toDouble()).toInt() }.toDouble()

                itemView.discounted_price.text = listdata?.price
            val marginpprice = listdata!!.mrp.toInt() - listdata.price.toInt()
                itemView.offer_percent.text = "Rs " + marginpprice.toString() +  " save"
                if(marginpprice ==0) {
                    itemView.red_offer_img.isVisible = false
                }else{
//                    itemView.item_price.strike = true
                    itemView.item_price.strike = true
                }
//                itemView.weight.text = listdata.weght

                itemView.total.text = listdata.quantity.toString()
                itemView.add.setOnClickListener(View.OnClickListener {

                    vibe.vibrate(80)
                    itemView.add_quantity.isVisible = true
                    itemView.add_new_item.isVisible = false
                    itemView.total.text = listdata.quantity.toString()

                })

                itemView.plus.setOnClickListener(View.OnClickListener {

                    listdata.quantity++
                    itemView.total.text = listdata.quantity.toString()
                    notifyDataSetChanged()
                })

                itemView.minus.setOnClickListener(View.OnClickListener {

                    if(listdata.quantity>1){
                        listdata.quantity--
                        itemView.total.text = listdata.quantity.toString()
                        notifyDataSetChanged()
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

        textView.text = "Rs: ${Global.pricing}"

    }
   inner class Category_Holder(val itemView:  ProductItemViewBinding) : RecyclerView.ViewHolder(itemView.root)

}
