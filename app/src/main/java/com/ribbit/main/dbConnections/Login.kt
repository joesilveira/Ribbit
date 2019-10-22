package com.ribbit.main.dbConnections

import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Login() {

    /*
    This Class contains the neccesasry functions and methods to log a user in to the database
    This class works with com.ribbit.main->Screens->LoginScreen as the running class
    This class works with res->layout->activity_login_screen.xml as the layout
     */


    //Variables
    var validUser = ""
    var postStatus = ""


    //Checks to see if user entry is valid
    fun isEntry(email: String, password: String): Boolean {
        if (email.contains('@') && (password.length > 5)) {
            return true
        }
        return false
    }

    //Method to log user in to database using http requests
    fun userLogin(email_address: String, password: String) {

        var reqParam = URLEncoder.encode("email_address", "UTF-8") + "=" + URLEncoder.encode(email_address, "UTF-8")
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
        val mURL = URL("http://134.209.119.180/Ribbit/db/logUserIn.php")


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
                        val response = StringBuilder()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        val testObj = JSONObject(response.toString())
                        validUser = testObj.get("validUser").toString()
                        postStatus = testObj.get("postStatus").toString()
                        it.close()

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



}











