package com.Frndzcart.urbanchoice.adapter

import android.content.Context
import android.graphics.Paint
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.databinding.ProductItemViewBinding
import com.Frndzcart.urbanchoice.fragment.Cartfragment
import com.Frndzcart.urbanchoice.`interface`.counter
import com.Frndzcart.urbanchoice.`interface`.setvisibility
import com.bumptech.glide.Glide

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
        var listdata = Global.cartList[position]

        holder.add_quantity.isVisible = true
        holder.add_new_item.isVisible = false
        holder.item_name.text = listdata?.title
        holder.item_price.text = context!!.resources.getString(R.string.rupees,listdata?.mrp)
        Global.pricing = Global.cartList.sumBy {( it!!.quantity * it.price.toDouble()).toInt() }.toDouble()

        holder.discounted_price.text = context!!.resources.getString(R.string.rupees,listdata?.price)
        val marginpprice = listdata!!.mrp.toInt() - listdata.price.toInt()
        holder.offer_percent.text = "Rs $marginpprice save"
            if(marginpprice ==0) {
                holder.red_offer_img.isVisible = false

            }else{
//                    itemView.item_price.strike = true
                holder.item_price.strike = true

            }
//                itemView.weight.text = listdata.weght

        holder.total.text = listdata.quantity.toString()
        holder.add.setOnClickListener(View.OnClickListener {

                vibe.vibrate(80)
                holder.add_quantity.isVisible = true
                holder.add_new_item.isVisible = false
                holder.total.text = listdata.quantity.toString()

            })
        val url = Global.BASE_Image_URL +"admin/icon_file/"+listdata.icon_file

        context?.let { Glide.with(it).load(url).into(holder.item_image) }

        holder.plus.setOnClickListener(View.OnClickListener {

                listdata.quantity++
                holder.total.text = listdata.quantity.toString()
                notifyDataSetChanged()
            })

            holder.minus.setOnClickListener(View.OnClickListener {

                if(listdata.quantity>1){
                    listdata.quantity--
                    holder.total.text = listdata.quantity.toString()
                    notifyDataSetChanged()
                }else{
                    holder.add_quantity.isVisible = false
                    holder.add_new_item.isVisible = true
                    Global.cartList.remove(listdata)
                }
                count.onCount(Global.cartList.size)
                visibiltu.setVisibilty(Global.cartList.size)
                notifyDataSetChanged()
            })

        textView.text = "Rs: ${Global.pricing}"

    }
   inner class Category_Holder(val itemView:  ProductItemViewBinding) : RecyclerView.ViewHolder(itemView.root) {

       val minus = itemView.findViewById<TextView>(R.id.minus)
       val total = itemView.findViewById<TextView>(R.id.total)
       val add = itemView.findViewById<TextView>(R.id.add)
       val plus = itemView.findViewById<TextView>(R.id.plus)
       val item_name = itemView.findViewById<TextView>(R.id.item_name)
       val item_price = itemView.findViewById<TextView>(R.id.item_price)
       val offer_percent = itemView.findViewById<TextView>(R.id.offer_percent)
       val discounted_price = itemView.findViewById<TextView>(R.id.discounted_price)
       val item_image = itemView.findViewById<ImageView>(R.id.item_image)
       val red_offer_img = itemView.findViewById<ImageView>(R.id.red_offer_img)
       val add_quantity = itemView.findViewById<LinearLayout>(R.id.add_quantity)
       val add_new_item = itemView.findViewById<LinearLayout>(R.id.add_new_item)
   }

}
