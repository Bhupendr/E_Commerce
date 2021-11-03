package com.Frndzcart.urbanchoice.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class MyCustomAppIntro : AppIntro2() {

    private var sharedPreference: SharedPreferences? = null
    private var editor :  SharedPreferences.Editor?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        isColorTransitionsEnabled = true
        setTransformer(AppIntroPageTransformerType.Depth)
        isIndicatorEnabled = true
         sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
         editor = sharedPreference?.edit()

        addSlide(AppIntroFragment.newInstance(
                title = "Welcome...",
                titleColor = resources.getColor(R.color.white),
                imageDrawable = R.drawable.launchericon,
                description = "Welcome to urbanchoice family",
                backgroundColor = resources.getColor(R.color.orange)
        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Fresh Meat...",
                titleColor = resources.getColor(R.color.white),
                imageDrawable = R.drawable.meat,
                description = "We will deliver fresh meat at your door step",
                descriptionColor = resources.getColor(R.color.white),
                backgroundColor = resources.getColor(R.color.red)
        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Delivery..",
                titleColor = resources.getColor(R.color.white),
                imageDrawable = R.drawable.delivery,
                description = "Fastest delivery within 60-90 mins",
                descriptionColor = resources.getColor(R.color.white),
                backgroundColor = resources.getColor(R.color.common_blue)
        ))


    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"


        editor?.putBoolean(Global.AppIntro, false)
        editor?.apply()
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }

    // Decide what to do when the user clicks on "Done"
    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        editor?.putBoolean(Global.AppIntro, false)
        editor?.apply()
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }

}
