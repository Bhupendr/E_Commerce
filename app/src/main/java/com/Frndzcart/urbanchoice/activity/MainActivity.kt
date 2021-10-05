package com.Frndzcart.urbanchoice.activity

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.Frndzcart.urbanchoice.Global.Global
import com.Frndzcart.urbanchoice.R
import com.Frndzcart.urbanchoice.`interface`.DrawerLock
import com.Frndzcart.urbanchoice.`interface`.counter
import com.Frndzcart.urbanchoice.databinding.ActivityMainBinding
import com.Frndzcart.urbanchoice.fragment.*
import com.google.android.material.navigation.NavigationView
import com.pixplicity.easyprefs.library.Prefs
import java.util.*

class MainActivity : AppCompatActivity(), counter,DrawerLock {


    private var toggle: ActionBarDrawerToggle? = null
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
       /* binding.search.isVisible = true
        binding.microphone.isVisible = true*/
        callFragment(Add_Product_fragment())
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.heading.text = Global.name
        val headerview : View = binding.navView.getHeaderView(0)
        val headerText : TextView = headerview.findViewById(R.id.header_title)
        val profile : ImageView = headerview.findViewById(R.id.profile)
        headerText.text = Prefs.getString(Global.Username,"")
        binding.heading.text = "Home"

        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()
        binding.microphone.setOnClickListener(View.OnClickListener {
            speechRecognizition()
        })


        profile.setOnClickListener {
            loadFragment(ProfileFragment())
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
//        loadFragment(HomeFragment())



    }

    @SuppressLint("ClickableViewAccessibility")
    private fun speechRecognizition() {

        val mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mSpeechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Search Product")
        try{
            startActivityForResult(mSpeechRecognizerIntent, 1)
        }catch (e: ActivityNotFoundException){

        }


    }
    private fun callFragment(fragment: Fragment) {
        if (Global.classname == fragment?.javaClass?.name)
            return

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> if (resultCode == RESULT_OK) {
                val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.search.setQuery(result?.get(0), true)
            }

        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (Global.classname == fragment?.javaClass?.name)
            return false
        Global.hideKeybaord(binding.search, this)
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            return true
        }
        return false
    }

    override fun onCount(size: Int) {
        if(size==0){
            binding.countLayout.isVisible = false
        }else{
            binding.countLayout.isVisible = true
            binding.count.text = size.toString()
        }
    }

    override fun setDrawerLocked(shouldLock: Boolean) {
        Global.hideKeybaord(binding.search, this)
        if(shouldLock){
//            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_left_back_)

        }else{

            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            binding.drawerLayout.addDrawerListener(toggle!!)
            toggle!!.syncState()
//            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            binding.heading.text = item.title.toString()
            when (item.itemId) {

                R.id.nav_home -> {
                    onCount(Global.cartList.size)
                    callFragment(Add_Product_fragment())
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.orders -> {
                    loadFragment(OrderFragment())
                    binding.countLayout.isVisible = false
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.feedback -> {
                    loadFragment(FeedbackFragment())
                    binding.countLayout.isVisible = false
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.help -> {
                    val phoneNumber = "+91-9905614474"
                    val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
                    try {
                        packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(Intent.createChooser(i,""))
                    } catch (e: PackageManager.NameNotFoundException) {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse(phoneNumber)
                        startActivity(Intent.createChooser(intent,""))
                        e.printStackTrace()
                    }

                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

            }
            false
        })

    }


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed()
    {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val fragments: Int = supportFragmentManager.backStackEntryCount
            if (fragments == 1) {
                // finish()
                if (doubleBackToExitPressedOnce) {
                 /*   moveTaskToBack(true)
                    exitProcess(-1)*/
                    finishAffinity()

                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    doubleBackToExitPressedOnce = false
                }, 2000)
            } else if (fragmentManager.backStackEntryCount > 1) {
                fragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}