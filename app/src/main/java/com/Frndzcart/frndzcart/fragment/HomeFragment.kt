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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.Frndzcart.frndzcart.model.Image_Slider_model
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.SliderAdapterExample

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {


    lateinit var fruits_vegetable_card: CardView

    private lateinit var root: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_home, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val sliderView: SliderView = root.findViewById(R.id.slider)

        val microphone : ImageView = activity?.findViewById(R.id.microphone)!!
        val search : SearchView = activity?.findViewById(R.id.search)!!
        search.isVisible = true
        microphone.isVisible = true

        val heading : TextView = activity?.findViewById(R.id.heading) as TextView
        heading.setText("Rupal Dhruv")

        fruits_vegetable_card = root.findViewById(R.id.fruits_vegetable_card)
        val imagelist = arrayListOf<Image_Slider_model>(
                Image_Slider_model(R.drawable.banner),
                Image_Slider_model(R.drawable.banner),
                Image_Slider_model(R.drawable.banner)
        )
        val sliderAdapter = SliderAdapterExample(imagelist)
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
        sliderView.setIndicatorSelectedColor(Color.GRAY)
        sliderView.setIndicatorUnselectedColor(Color.WHITE)
        sliderView.setScrollTimeInSec(2)
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()


        fruits_vegetable_card.setOnClickListener(View.OnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, Add_Product_fragment())
            transaction?.addToBackStack("back")
            transaction?.commit()
        })
        return root
    }

}
