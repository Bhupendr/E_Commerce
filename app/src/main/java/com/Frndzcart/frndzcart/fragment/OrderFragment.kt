package com.Frndzcart.frndzcart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.Frndzcart.frndzcart.R

class OrderFragment : Fragment() {

    private lateinit var root :View
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_order, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        return root


    }

}
