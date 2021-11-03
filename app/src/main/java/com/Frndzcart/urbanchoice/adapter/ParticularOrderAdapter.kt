package com.Frndzcart.urbanchoice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.urbanchoice.databinding.ParticularitemDetailsBinding
import com.Frndzcart.urbanchoice.model.ItemDetail
import kotlinx.android.synthetic.main.particularitem_details.view.*

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
        with(holder) {
            var listdata = itemDetail[position]
            itemView.item_name.text = listdata.name + "X" + listdata.qty
           itemView.price.text = "Rs." + listdata.price.toString()

        }


    }
    inner class Category_Holder(itemView: ParticularitemDetailsBinding) : RecyclerView.ViewHolder(itemView.root) {

    }
}
