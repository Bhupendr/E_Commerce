package com.Frndzcart.frndzcart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.Frndzcart.frndzcart.databinding.AddressSectionBinding

class AddressSectionFragment : Fragment() {

    lateinit var binding: AddressSectionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddressSectionBinding.inflate(layoutInflater)
        return binding.root
    }
}
