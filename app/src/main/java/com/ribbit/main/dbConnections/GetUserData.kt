package com.ribbit.main.dbConnections

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class GetUserData() {



    //Class Variables
    private var jsonObj : JSONObject = JSONObject()

    //User Variables
    private var userFirstName = ""
    private var userLastName = ""
    private var userEmail = ""
    private var userPhoneNumber = ""
    private var userDOB = ""
    private var userBio = ""


    fun getData(email: String) : Unit {
        var reqParam =
            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")

        println(reqParam)


        //URL to ping
        val mURL = URL("http://134.209.119.180/Ribbit/db/getRibbitUser.php")


        with(mURL.openConnection() as HttpURLConnection) {

            try {
                requestMethod = "POST"


                //Write email and password to php file
                val wr = OutputStreamWriter(outputStream)
                wr.write(reqParam)

                wr.flush()

                //Check if php server repsonse is valid
                if (this.responseCode == 200) {
                    println("URL : $url")
                    println("Response Code : $responseCode")

                    //Read input back from php file in json
                    BufferedReader(InputStreamReader(inputStream)).use {
                       var inputLine = it.readLine()
                        var response = StringBuilder()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        it.close()
                        jsonObj = JSONObject(response.toString())

                    }

                    //If php file is invalid print
                } else if (this.responseCode == 500) {
                    println("Internal Server Error")
                }

                //catch from try http request
            } catch (e: Exception) {
                e.printStackTrace()

            }

        }

    }

    fun getUserFirstName(email: String) : String{
        getData(email)
        var result  = jsonObj.get("first_name").toString()
        userFirstName = result
        return userFirstName
    }

    fun getUserLastName(email: String) : String{
        getData(email)
        var result = jsonObj.get("last_name").toString()
        userLastName = result
        return userLastName
    }
    fun getUserEmail(email: String) : String{
        getData(email)
        var result = jsonObj.get("email").toString()
        userEmail = result
        return userEmail
    }
    fun getUserPhoneNumber(email: String) : String{
        getData(email)
        var result = jsonObj.get("phone_number").toString()
        userPhoneNumber = result
        return userPhoneNumber
    }
    fun getUserDOB(email: String):String{
        getData(email)
        var result = jsonObj.get("dob").toString()
        userDOB = result
        return userDOB
    }
    fun getUserBio(email: String): String{
        getData(email)
        var result = jsonObj.get("bio").toString()
        userBio = result
        return userBio
    }
    fun getUserFullName(email: String):String{
        var name = getUserFirstName(email).capitalize() + " " + getUserLastName(email).capitalize()
        return name
    }

}