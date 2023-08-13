package com.example.recyclerproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    // Declarations for resources

    lateinit var progressbar: ProgressBar
    lateinit var recyclerview : RecyclerView
    lateinit var customAdapter: RecyclerAdapter
    lateinit var productList: ArrayList<ItemViewModel>


   
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerView)
        progressbar = findViewById(R.id.progressBar)

        progressbar.visibility = View.VISIBLE

        val client = AsyncHttpClient(true, 80, 443)
        // pass the productlist to adapter

        customAdapter = RecyclerAdapter(applicationContext)
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.setHasFixedSize(true)

        client.get(this, "https://erick1259.pythonanywhere.com/products",null,"application/json",
            object: JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray?
                ) {
                    val gson = GsonBuilder().create()
                    val list = gson.fromJson(response.toString(),
                    Array<ItemViewModel>::class.java).toList()

                    customAdapter.setProductListItems(list)

                    progressbar.visibility = View.GONE
                    //super.onSuccess(statusCode, headers, response)
                }


                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {

                    Toast.makeText(applicationContext, "No data Available"+ statusCode, Toast.LENGTH_SHORT).show()
                    progressbar.visibility = View.GONE

                   // super.onFailure(statusCode, headers, responseString, throwable)
                }


            } // end response handler
        ) // end client
     recyclerview.adapter = customAdapter

    }

}