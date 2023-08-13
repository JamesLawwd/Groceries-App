package com.example.recyclerproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject


class LoginActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var email = findViewById<EditText>(R.id.email)
        var userPassword = findViewById<EditText>(R.id.password)


        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)

        }
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.GONE

        var signUpBtn = findViewById<Button>(R.id.submit_button)
        signUpBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            var payload = JSONObject()

            payload.put("email", email.text.toString())

            payload.put("password", userPassword.text.toString())


            var conBody = StringEntity(payload.toString())

            var client = AsyncHttpClient(true, 80, 443)
            client.post(this, "https://ghostsec.pythonanywhere.com/register", conBody,
                "application/json",
                object : JsonHttpResponseHandler() {

                    // onSuccess
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {

                        when (statusCode) {

                            10 -> Toast.makeText(
                                applicationContext,
                                "Invalid Email",
                                Toast.LENGTH_SHORT
                            ).show()
                            30 -> Toast.makeText(
                                applicationContext,
                                "Have atleast 8 characters password",
                                Toast.LENGTH_SHORT
                            ).show()
                            40 -> Toast.makeText(
                                applicationContext,
                                "Passwords dont match!",
                                Toast.LENGTH_SHORT
                            ).show()

                            200 -> {
                                Toast.makeText(
                                    applicationContext,
                                    "Saved Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                var i = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                        }

                    }


                } // end Handler


            ) // end client
        }


    }
}
