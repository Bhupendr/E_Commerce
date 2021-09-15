package com.Frndzcart.frndzcart.activity

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SearchView
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
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity(), counter,DrawerLock {


    var toggle: ActionBarDrawerToggle? = null
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

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.open, R.string.close)

        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()
        binding.microphone.setOnClickListener(View.OnClickListener {
            speechRecognizition()
        })
        binding.cart.setOnClickListener(View.OnClickListener {
            binding.search.isVisible = false
            binding.microphone.isVisible = false
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Cartfragment())
            transaction.addToBackStack("back")
            transaction.commit()
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
                    binding.countLayout.isVisible = false
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

            }
            false
        })

    }
}