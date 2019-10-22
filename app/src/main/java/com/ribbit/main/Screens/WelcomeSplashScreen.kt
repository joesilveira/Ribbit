package com.ribbit.main.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.ribbit.main.R

class WelcomeSplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_splash_screen)

        //try database connection


        //make full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_welcome_splash_screen)

        //4 second splash
        Handler().postDelayed({
            //start
            var start = Intent(this@WelcomeSplashScreen, LoginScreen::class.java).apply {
                startActivity(this)
            }
            finish()
            //Reset to 4000 after finished testing purposes
        }, 1000)
    }
}
