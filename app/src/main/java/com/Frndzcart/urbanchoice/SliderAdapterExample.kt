package com.Frndzcart.urbanchoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.Frndzcart.urbanchoice.model.Image_Slider_model

import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapterExample(var imagelist: ArrayList<Image_Slider_model>) : SliderViewAdapter<SliderAdapterExample.PhotoHolder>() {
    class PhotoHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {
        var imageview : ImageView = itemView!!.findViewById(R.id.imageview)
    }

    override fun getCount(): Int {
        return imagelist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterExample.PhotoHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.image_slider, parent, false)
        return SliderAdapterExample.PhotoHolder(view)

    }

    override fun onBindViewHolder(viewHolder: SliderAdapterExample.PhotoHolder?, position: Int) {
        var listdata = imagelist[position]
        viewHolder?.imageview?.setBackgroundResource(listdata.item_pic)
    }

}
