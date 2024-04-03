package com.modcom.antonyappapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    lateinit var productList:ArrayList<Product>
    lateinit var recyclerAdapter: RecyclerAdapter //call the adapter
    lateinit var progressbar: ProgressBar
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //modcom.pythonanywhere.com/api/all
        //github.com/modcomlearning/shop

        recyclerView = findViewById(R.id.recycler)
        progressbar= findViewById(R.id.progressbar)
        progressbar.visibility = View.VISIBLE
        val client = AsyncHttpClient(true,80,443)
        recyclerAdapter = RecyclerAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)


        client.get(this, "https://android-payment-api.onrender.com/api/all/",
            null,
            "application/json",
            object: JsonHttpResponseHandler(){
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                    //we convert json array to a list of a given model
                    val gson = GsonBuilder().create()
                    val list = gson.fromJson(response.toString(),
                        Array<Product>::class.java).toList()
                    //now pass the converted list to adapter
                    recyclerAdapter.setProductListItems(list)
                    progressbar.visibility = View.GONE
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(applicationContext, "no products on sale"+statusCode, Toast.LENGTH_LONG).show()
                    progressbar.visibility = View.GONE
                }
            }//end handler
        )//end post

        //now put the adapter to recycler view
        recyclerView.adapter = recyclerAdapter
    }
}




