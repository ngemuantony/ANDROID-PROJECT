package com.modcom.antonyappapi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        //Get the 4 fields from prefferences and display them in respective view
        val prefs = getSharedPreferences("db", Context.MODE_PRIVATE)

        val product_name = findViewById<TextView>(R.id.product_name)
        product_name.text = prefs.getString("product_name", "")

        val product_desc = findViewById<TextView>(R.id.product_desc)
        product_desc.text = prefs.getString("product_desc", "")

        val product_cost = findViewById<TextView>(R.id.product_cost)
        product_cost.text = prefs.getString("product_cost", "")

        val image_url = findViewById<ImageView>(R.id.image_url)
        Glide.with(applicationContext).load(prefs.getString("image_url", ""))
            .apply(RequestOptions().centerCrop())
            .into(image_url)//put into the imageview
    }
}