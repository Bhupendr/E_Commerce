package com.Frndzcart.urbanchoice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.databinding.ParticularitemDetailsBinding
import com.Frndzcart.urbanchoice.model.ItemDetail

class ParticularOrderAdapter(val itemDetail: ArrayList<ItemDetail>) : RecyclerView.Adapter<ParticularOrderAdapter.Category_Holder>(){

    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Category_Holder {

        val binding = ParticularitemDetailsBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

       context = parent.context


        return Category_Holder(binding)
    }

    override fun getItemCount(): Int {
        return itemDetail.size

    }


    override fun onBindViewHolder(holder: Category_Holder, position: Int) {
        var listdata = itemDetail[position]
        holder.item_name.text = listdata.name + "X" + listdata.qty
        holder.price.text = "Rs." + listdata.price.toString()


    }
    inner class Category_Holder(itemView: ParticularitemDetailsBinding) : RecyclerView.ViewHolder(itemView.root) {


        val item_name = itemView.itemName
        val price = itemView.price

    }
}
