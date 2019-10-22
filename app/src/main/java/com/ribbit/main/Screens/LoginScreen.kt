package com.ribbit.main.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ribbit.main.dbConnections.Login
import android.os.StrictMode
import android.view.Window
import android.view.WindowManager
import com.ribbit.main.R
import com.ribbit.main.dbConnections.GetUserData


class LoginScreen : AppCompatActivity() {


    var gud = GetUserData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login_screen)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)


        var login = Login()
        var signInBtn = findViewById<Button>(R.id.loginButton)

        var createAccountBtn = findViewById<Button>(R.id.createAccountButton)

        //Execute when user clicks create account
        createAccountBtn.setOnClickListener{
            var i = Intent(this@LoginScreen,CreateAccountScreen::class.java)
            startActivity(i)
        }


        //Execute when user clicks login
        signInBtn.setOnClickListener {

            //Get user entrys
            var email = findViewById<EditText>(R.id.username).text.toString()
            var password = findViewById<EditText>(R.id.password).text.toString()

            if(login.isEntry(email,password)) {
                login.userLogin(email, password)
                if(login.validUser == "true" && login.postStatus == "correct"){
                    Toast.makeText(applicationContext,"Success!",Toast.LENGTH_SHORT).show()

                    //Pass user email addresss to home screen when the activity is started
                    var i = Intent(this@LoginScreen, HomeScreen::class.java)
                    i.putExtra("userEmail",email)
                    startActivity(i)
                }else{
                    Toast.makeText(applicationContext,"Invalid email or password",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(applicationContext,"Please supply a valid email",Toast.LENGTH_SHORT).show()
            }

        }
    }
}





