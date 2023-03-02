package com.markrizkalla.dishapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.markrizkalla.dishapp.databinding.ActivitySplachBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplachBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplachBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}