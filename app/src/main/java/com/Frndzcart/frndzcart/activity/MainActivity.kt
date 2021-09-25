package com.Frndzcart.frndzcart.activity

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.Frndzcart.frndzcart.Global.Global
import com.Frndzcart.frndzcart.fragment.HomeFragment
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.`interface`.DrawerLock
import com.Frndzcart.frndzcart.databinding.ActivityMainBinding
import com.Frndzcart.frndzcart.fragment.Cartfragment
import com.Frndzcart.frndzcart.`interface`.counter
import com.Frndzcart.frndzcart.`interface`.setvisibility
import com.Frndzcart.frndzcart.fragment.OrderFragment
import com.google.android.material.navigation.NavigationView
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
        binding.search.isVisible = true
        binding.microphone.isVisible = true

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close)

        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()
        binding.microphone.setOnClickListener(View.OnClickListener {
            speechRecognizition()
        })

        loadFragment(HomeFragment())


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
        Global.hideKeybaord(binding.search,this)
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
        Global.hideKeybaord(binding.search,this)
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
            when (item.itemId) {
                R.id.nav_home -> {
                    onCount(Global.cartList.size)
                    loadFragment(HomeFragment())
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.orders ->{
                    loadFragment(OrderFragment())
                    binding.countLayout.isVisible = false
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
            var fragments: Int = getSupportFragmentManager().getBackStackEntryCount()
            if (fragments == 1) {
                // finish()
                if (doubleBackToExitPressedOnce) {
                    System.exit(0)
                    return
                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            } else if (fragmentManager.backStackEntryCount > 1) {
                fragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}