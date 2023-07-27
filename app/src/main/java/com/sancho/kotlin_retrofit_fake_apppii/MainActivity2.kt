package com.sancho.kotlin_retrofit_fake_apppii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sancho.kotlin_retrofit_fake_apppii.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent=intent
        val title=intent.getStringExtra("title")
        val image=intent.getStringExtra("image")
        val description=intent.getStringExtra("description")
        val price=intent.getDoubleExtra("price",0.0)
        val rating=intent.getDoubleExtra("rating",0.0)

        binding.apply {
            textviewname.text=title
            Glide.with(this@MainActivity2).load(image).into(imageview)
            textviewdescription.text=description
            textviewprice.text="$price $"
            ratingbar.rating=rating.toFloat()
        }

    }
}