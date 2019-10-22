package com.ribbit.main.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.ribbit.main.R
import com.ribbit.main.dbConnections.AddUser
import com.ribbit.main.dbConnections.Login
import java.util.*

class CreateAccountScreen : AppCompatActivity() {

        var au : AddUser = AddUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_create_account)



        //Get Button Click and set event listener
        var createAccountBtn = findViewById<ImageButton>(R.id.imageButton)


        createAccountBtn.setOnClickListener{



            //Get user fields
            var fName = findViewById<TextView>(R.id.firstName).text.toString()
            var lName = findViewById<TextView>(R.id.lastName).text.toString()
            var phoneNum = findViewById<TextView>(R.id.phoneNumber).text.toString()
            var emailAddress = findViewById<TextView>(R.id.emailAddress2).text.toString()
            var password = findViewById<TextView>(R.id.password2).text.toString()
            var dob = findViewById<TextView>(R.id.dob).text.toString()


            //Check to see if user entered all fields
            if(au.isValidEntry(fName,lName,password,phoneNum,emailAddress,dob) == true){

                //Convert users dob to match database field
                dob = au.convertDate(dob)
                println(dob)


                //Checks to see if user entered an email and a password greater than 5 characters
                if(Login().isEntry(emailAddress,password)){
                    Toast.makeText(applicationContext,"Attempting to create user...",Toast.LENGTH_SHORT).show()

                    //Attempt to create new user through http request to server
                    au.addUser(fName,lName,password,phoneNum,emailAddress,dob)

                    if(au.jsonResponse.get("post_status") == "user exists"){
                        Toast.makeText(applicationContext,"User Exists with This Email Supplied",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext,"User Created! Please Log In!",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(applicationContext,"Please enter a valid email address or a password greater than 5 characters",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext,"Please Enter All Fields",Toast.LENGTH_SHORT).show()
            }




        }


    }


}
