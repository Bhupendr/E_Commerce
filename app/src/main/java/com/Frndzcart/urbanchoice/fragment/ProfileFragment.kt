package com.Frndzcart.urbanchoice.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.activity.LogInActivity
import com.Frndzcart.urbanchoice.databinding.ProfilepageBinding
import com.pixplicity.easyprefs.library.Prefs

class ProfileFragment : Fragment() {

    private lateinit var binding : ProfilepageBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = ProfilepageBinding.inflate(layoutInflater)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        Global.classname = javaClass.name
        binding.nameValue.text = Prefs.getString(Global.Username,"")
        binding.mobileValue.text =Prefs.getString(Global.mobilenumber,"")
        binding.emailValue.text =Prefs.getString(Global.email,"")
        binding.addressValue.text =Prefs.getString(Global.address,"")
        val heading : TextView = activity?.findViewById(R.id.heading) as TextView
        heading.text = "Profile"
        val cart : ImageView  = activity?.findViewById(R.id.cart)!!
        cart.isVisible = false
        binding.logout.setOnClickListener{

            Prefs.clear().apply()
            val intent = Intent(context, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            (activity as AppCompatActivity).finish()

        }


        return binding.root
    }

}
