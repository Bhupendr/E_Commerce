package com.Frndzcart.urbanchoice.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
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
        val sp1 = getSharedPreferences("Login", MODE_PRIVATE)

       /* if(sp1!=null){
            val unm = sp1.getString("Unm", null)
            val pass = sp1.getString("Psw", null)
            val autologin = sp1.getBoolean("relogin", false)

            if(autologin){
                val act = LogInActivity()
                act.LogIn(unm!!,pass!!)
            }

        }*/
        val motionLayout: MotionLayout = findViewById(R.id.motion_layout)
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {}
            override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) {}
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {

                if(Prefs.getBoolean(Global.autoLogin,false)){
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                }else {
                    val intent = Intent(this@SplashActivity, LogInActivity::class.java)
                    startActivity(intent)

                }
                finish()
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


