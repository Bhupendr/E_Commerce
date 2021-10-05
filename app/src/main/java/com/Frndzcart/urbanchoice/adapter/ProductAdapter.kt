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
import com.Frndzcart.urbanchoice.model.ProductResponseItem
import com.Frndzcart.urbanchoice.`interface`.counter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.Category_Holder>()  {
    lateinit var context : Context
    lateinit var vibe : Vibrator
    lateinit var count : counter
    var arrayList =  ArrayList<ProductResponseItem>()
    constructor(arrayList: ArrayList<ProductResponseItem>, context: Context) : this() {
        this.arrayList = arrayList
        this.context = context
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Category_Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item_view,
            parent,
            false
        )


        count = parent.context as counter
        vibe = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        return Category_Holder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    override fun onBindViewHolder(holder: Category_Holder, position: Int) {


        var listdata = arrayList[position]
        if(checkItemInCart(listdata)){
            holder.add_quantity.isVisible = true
            holder.add_new_item.isVisible = false
            holder.total.text = listdata.quantity.toString()
        }
        holder.item_name.text = listdata.title
        holder.item_price.text = "Rs." + listdata.mrp
        holder.discounted_price.text = "Rs." + listdata.price
        holder.total.text = listdata.quantity.toString()
        val url = Global.BASE_Image_URL +"admin/icon_file/"+listdata.icon_file
        Log.e("url", url)
        Glide.with(context).load(url).into(holder.item_image)
/*
          .placeholder(R.drawable.ic_baseline_camera_alt_24) //5
            .error(R.drawable.ic_baseline_broken_image_24) //6
            .fallback(R.drawable.ic_baseline_camera_alt_24)
            .dontAnimate()   */

        val marginpprice = listdata.mrp.toInt() - listdata.price.toInt()

        holder.offer_percent.text = "Rs " + marginpprice.toString() +  " save"
        if(marginpprice ==0){
            holder.red_offer_img.isVisible = false
        }else{
            holder.item_price.strike = true
        }
//        holder.weight.text = listdata.weght

        holder.add.setOnClickListener(View.OnClickListener {
            listdata.quantity = 1
            vibe.vibrate(80)
            holder.add_quantity.isVisible = true
            holder.add_new_item.isVisible = false
            holder.total.text = listdata.quantity.toString()



            if(addtolist(listdata) ){
                Global.cartList.add(listdata)
            }

            count.onCount(Global.cartList.size)

        })

        holder.plus.setOnClickListener(View.OnClickListener {
            listdata.quantity++
            holder.total.text = listdata.quantity.toString()
            addquantitytolist(listdata)

        })

        holder.minus.setOnClickListener(View.OnClickListener {

            if(listdata.quantity>1){
                listdata.quantity--
                holder.total.text = listdata.quantity.toString()
                addquantitytolist(listdata)
            }else{
                holder.add_quantity.isVisible = false
                holder.add_new_item.isVisible = true
                Global.cartList.remove(listdata)
                deleteitem(listdata)

            }
            count.onCount(Global.cartList.size)
            // notifyDataSetChanged()
        })

    }

    private fun deleteitem(listdata: ProductResponseItem) {
        for (im:ProductResponseItem? in Global.cartList){
            if(im!!.id.equals(listdata.id)){
                Global.cartList.remove(listdata)
            }
//            notifyDataSetChanged()
        }
    }

    private fun addquantitytolist(listdata: ProductResponseItem) {
        for(im : ProductResponseItem? in Global.cartList){
            if(im!!.id.equals(listdata.id)){
                Global.cartList.set(Global.cartList.indexOf(im),listdata)
            }
//            notifyDataSetChanged()


        }


    }

    private fun checkItemInCart( listdata: ProductResponseItem):Boolean {
        for(im : ProductResponseItem? in Global.cartList){
            if(im!!.id.equals(listdata.id)){
                listdata.quantity = im.quantity
                return true
            }
        }
        return false
    }


    private fun addtolist(listdata: ProductResponseItem): Boolean {
        for (im: ProductResponseItem? in Global.cartList){
            if(im!!.id.equals(listdata.id)){

                im.quantity = im.quantity + listdata.quantity

                return false

            }

        }
        return true
    }




    class Category_Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var red_offer_img : ImageView = itemView.findViewById(R.id.red_offer_img)
        var item_name : TextView = itemView.findViewById(R.id.item_name)
        var item_price : TextView = itemView.findViewById(R.id.item_price)
        var item_image : ImageView = itemView.findViewById(R.id.item_image)
        var discounted_price : TextView = itemView.findViewById(R.id.discounted_price)

        var offer_percent : TextView = itemView.findViewById(R.id.offer_percent)
        var weight : TextView = itemView.findViewById(R.id.weight)

        var add : TextView = itemView.findViewById(R.id.add)
        var minus : TextView = itemView.findViewById(R.id.minus)
        var plus : TextView = itemView.findViewById(R.id.plus)
        var total : TextView = itemView.findViewById(R.id.total)
        var add_quantity : LinearLayout = itemView.findViewById(R.id.add_quantity)
        var add_new_item : LinearLayout = itemView.findViewById(R.id.add_new_item)

    }
}


inline var TextView.strike: Boolean
    set(visible) {
        paintFlags = if (visible) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
    get() = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG == Paint.STRIKE_THRU_TEXT_FLAG