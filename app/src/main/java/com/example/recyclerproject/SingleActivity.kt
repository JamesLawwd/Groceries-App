package com.example.recyclerproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class SingleActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        // Locate the Shared Preference
        val prefs:SharedPreferences = getSharedPreferences("productDB", Context.MODE_PRIVATE)

        val productName = prefs.getString("productName", "")
        val name = findViewById<TextView>(R.id.productName)
        name.text = productName

        val productCost = prefs.getString("productCost", "")
        val cost = findViewById<TextView>(R.id.product)

        cost.text = productCost


        val productDesc = prefs.getString("productDesc", "")
        val desc = findViewById<TextView>(R.id.productDesc)

        desc.text = productDesc

        val image = prefs.getString("image", "")
        val imageView = findViewById<ImageView>(R.id.image)
        Glide.with(applicationContext).load(image)
                .apply(RequestOptions().centerCrop())
                 .into(imageView)

        val payBtn = findViewById<Button>(R.id.btnPay)
        payBtn.setOnClickListener {

            val client = AsyncHttpClient(true, 80, 443)
            val payload = JSONObject()
            payload.put("phone", "254740922861")
            payload.put("amount", 1)

            val body = StringEntity(payload.toString())

            // loopj

            client.post(this, "https://erick1259.pythonanywhere.com/payment", body, "application/json",

                object : JsonHttpResponseHandler() {

                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {

                        Toast.makeText(applicationContext,
                            "Please Complete Payment on Your Phone$statusCode", Toast.LENGTH_LONG).show()
                        //super.onSuccess(statusCode, headers, response)
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        responseString: String?,
                        throwable: Throwable?
                    ) {

                        Toast.makeText(applicationContext,"Payment Incomplete Try Again$statusCode", Toast.LENGTH_LONG).show()

                        //super.onFailure(statusCode, headers, responseString, throwable)
                    }


                } // end handler

                ) // end client



        }

    }
}