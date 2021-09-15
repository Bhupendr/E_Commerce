package com.Frndzcart.frndzcart.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.Frndzcart.frndzcart.R
import com.Frndzcart.frndzcart.databinding.LoginPageBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding : LoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}