package com.markrizkalla.dishapp.view.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import com.markrizkalla.dishapp.R
import com.markrizkalla.dishapp.databinding.ActivitySplachBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplachBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplachBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //        HDE STATUS BAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


        val splashAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        binding.appName.animation = splashAnimation

        splashAnimation.setAnimationListener(object:AnimationListener{
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
//                to Handel things with a delay
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                },1000)
            }

            override fun onAnimationRepeat(p0: Animation?) {}

        })

    }
}