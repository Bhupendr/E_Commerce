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
import com.Frndzcart.urbanchoice.model.ProductResponseItem
import com.Frndzcart.urbanchoice.model.ProductViewModel


class FeedbackFragment : Fragment() {


    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.feedback_form, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        Global.classname = javaClass.name
//        (activity as DrawerLock?)!!.setDrawerLocked(true)


        val microphone : ImageView  = activity?.findViewById(R.id.microphone)!!
        val phone : ImageView  = activity?.findViewById(R.id.cart)!!
        val search : SearchView = activity?.findViewById(R.id.search)!!
        phone.isVisible = false
      /*  search.isVisible = true
        microphone.isVisible = true*/
        /*val heading : TextView = activity?.findViewById(R.id.heading) as TextView
        heading.text = "Add Product"*/


        return root
    }


}
