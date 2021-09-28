package com.Frndzcart.frndzcart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.frndzcart.Global.Global
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.model.OrderData

class OrderAdapter(var arrayList: ArrayList<OrderData>): RecyclerView.Adapter<OrderAdapter.Category_Holder>()   {

    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Category_Holder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.order_adapter,
                parent,
                false)
        context = parent.context
        return Category_Holder(view)
    }

    override fun onBindViewHolder(holder: Category_Holder, position: Int) {
        var listdata = arrayList[position]

        holder.title.text = Global.name
        holder.date_value.text = listdata.created_at
        holder.total_amount.text = listdata.total_amount
        holder.delivry_address.text = ": " +listdata.address
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class Category_Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var title : TextView = itemView.findViewById(R.id.title)
        var date_value : TextView = itemView.findViewById(R.id.date_value)
        var total_amount : TextView = itemView.findViewById(R.id.total_amount)
        var delivry_address : TextView = itemView.findViewById(R.id.delivry_address)







    }
}

