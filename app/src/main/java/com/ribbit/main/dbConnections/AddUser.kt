package com.ribbit.main.dbConnections

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class AddUser() {

    var jsonResponse = JSONObject()

    /*
    This Class contains the neccesasry functions and methods to add a new Ribbit user to the database
    This class works with com.ribbit.main->Screens->CreateAccountScreen adn the running class
    This class works with res->layout->activity_create_account.xml as the layout
     */


    //function to convert user D.O.B entry to match database field
    fun convertDate(date: String):String {
        var date1 = date.split('/')
        var dob = date1[2] + "-" + date1[0] + "-" + date1[1]
        return dob
    }

    //Function to check is user entry is valid (User entered all required fields)
    fun isValidEntry(
        first_name: String,
        last_name: String,
        password: String,
        phone_number: String,
        email: String,
        birthday: String
    ): Boolean {
        if (first_name.isNotBlank() && last_name.isNotBlank() && password.isNotBlank()
            && phone_number.isNotBlank() && email.isNotBlank() && birthday.isNotBlank()) {
            return true
        }
        return false
    }

    //Function to add user to the database with the given parameters
    fun addUser(
        first_name: String,
        last_name: String,
        password: String,
        phone_number: String,
        email: String,
        birthday: String
    ) {

        var reqParam =

            //encode first name
            URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(first_name, "UTF-8")

        //encode last name
        reqParam += "&" + URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(
            last_name,
            "UTF-8"
        )

        //Encode password
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(
            password,
            "UTF-8"
        )

        //Encode phone number
        reqParam += "&" + URLEncoder.encode("phone_number", "UTF-8") + "=" + URLEncoder.encode(
            phone_number,
            "UTF-8"
        )

        //Encode email
        reqParam += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(
            email,
            "UTF-8"
        )

        //Encode Birthday
        reqParam += "&" + URLEncoder.encode("birthday", "UTF-8") + "=" + URLEncoder.encode(
            birthday,
            "UTF-8"
        )

        println(reqParam)


        //URL to ping
        val mURL = URL("http://134.209.119.180/Ribbit/db/addRibbitUser.php")


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

                         jsonResponse = JSONObject(response.toString())

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
