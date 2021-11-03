package com.Frndzcart.urbanchoice.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.pixplicity.easyprefs.library.Prefs


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )
        setContentView(R.layout.splash_screen)
        Global.classname = javaClass.name
        val motionLayout: MotionLayout = findViewById(R.id.motion_layout)
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {}
            override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) {}
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                if(sharedPreference.getBoolean(Global.AppIntro,true)){
                    startActivity(Intent(this@SplashActivity, MyCustomAppIntro::class.java))

                }else{
                    if(Prefs.getBoolean(Global.autoLogin,false)){
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                    }else {
                        startActivity(Intent(this@SplashActivity, LogInActivity::class.java))

                    }
                }





            }
            override fun onTransitionTrigger(
                motionLayout: MotionLayout,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }
}


