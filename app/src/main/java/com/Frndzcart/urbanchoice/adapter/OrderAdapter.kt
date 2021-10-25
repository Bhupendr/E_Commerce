package com.Frndzcart.urbanchoice.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.activity.OrderDetails
import com.Frndzcart.urbanchoice.model.OrderData
import com.pixplicity.easyprefs.library.Prefs

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

        holder.title.text = Prefs.getString(Global.Username,"")
        holder.date_value.text = listdata.created_at
        holder.total_amount.text = listdata.total_amount
        holder.delivry_address.text = ": " +listdata.address

        holder.itemView.setOnClickListener{

            val bundle = Bundle()
            bundle.putParcelable(Global.ParticularOrder, listdata)
            val intent = Intent(context, OrderDetails::class.java)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
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

