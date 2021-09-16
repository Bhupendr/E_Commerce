package com.Frndzcart.frndzcart.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.Frndzcart.frndzcart.model.Image_Slider_model
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.SliderAdapterExample
import com.Frndzcart.frndzcart.`interface`.DrawerLock
import com.Frndzcart.frndzcart.activity.MainActivity
import com.Frndzcart.frndzcart.databinding.FragmentHomeBinding

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {


    lateinit var binding : FragmentHomeBinding



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()


        val microphone : ImageView = activity?.findViewById(R.id.microphone)!!
        val search : SearchView = activity?.findViewById(R.id.search)!!
        search.isVisible = true
        microphone.isVisible = true

        val heading : TextView = activity?.findViewById(R.id.heading) as TextView
        heading.setText("Rupal Dhruv")

        val imagelist = arrayListOf<Image_Slider_model>(
                Image_Slider_model(R.drawable.banner),
                Image_Slider_model(R.drawable.banner),
                Image_Slider_model(R.drawable.banner)
        )
        val sliderAdapter = SliderAdapterExample(imagelist)
        binding.slider.setSliderAdapter(sliderAdapter)
        binding.slider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.slider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
        binding.slider.setIndicatorSelectedColor(Color.GRAY)
        binding.slider.setIndicatorUnselectedColor(Color.WHITE)
        binding.slider.setScrollTimeInSec(2)
        binding.slider.isAutoCycle = true
        binding.slider.startAutoCycle()

        (activity as MainActivity).binding.cart.setOnClickListener(View.OnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, Cartfragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        })
        binding.fruitsVegetableCard.setOnClickListener(View.OnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, Add_Product_fragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as DrawerLock?)!!.setDrawerLocked(false)
        val toolbar = activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar?.setNavigationOnClickListener{
            (activity as MainActivity).binding.drawerLayout.openDrawer(GravityCompat.START)
        }

    }
}
