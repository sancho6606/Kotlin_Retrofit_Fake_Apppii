package com.sancho.kotlin_retrofit_fake_apppii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sancho.kotlin_retrofit_fake_apppii.model.ProductModelItem
import com.sancho.kotlin_retrofit_fake_apppii.retrofit.RetrofitInstance.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var productAdapter: ProductAdapter
    lateinit var progressBar: ProgressBar
    var arrayList=ArrayList<ProductModelItem>()
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.recyclerview)
        progressBar=findViewById(R.id.progressbar1)
        swipeRefreshLayout=findViewById(R.id.swiperefresh)

        getAllProducts()
        swipeRefreshLayout.setOnRefreshListener {
            Log.i("sancho", "onRefresh called from SwipeRefreshLayout")

            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            getAllProducts()
        }



    }

    fun getAllProducts(){
        val call:Call<ArrayList<ProductModelItem>> = api.getAllProducts()
//        progressBar.visibility=View.VISIBLE
        call.enqueue(object :Callback<ArrayList<ProductModelItem>>{
            override fun onResponse(
                call: Call<ArrayList<ProductModelItem>>,
                response: Response<ArrayList<ProductModelItem>>
            ) {
                if (response.isSuccessful){
                    progressBar.visibility=View.INVISIBLE

                    arrayList=response.body()!!
                    Log.d("sancho","onResponse: ${response.body()?.get(0)?.title}")
                    recyclerView.layoutManager=GridLayoutManager(this@MainActivity,2)
                    productAdapter= ProductAdapter(this@MainActivity,arrayList)
                    recyclerView.adapter=productAdapter
                    swipeRefreshLayout.isRefreshing=false

                }else{
                    progressBar.visibility=View.INVISIBLE
                    swipeRefreshLayout.isRefreshing=false
                }
            }

            override fun onFailure(call: Call<ArrayList<ProductModelItem>>, t: Throwable) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            // Check if user triggered a refresh:
            R.id.menu_refresh -> {
                Log.i("LOG_TAG", "Refresh menu item selected")

                // Signal SwipeRefreshLayout to start the progress indicator
                swipeRefreshLayout.isRefreshing = true

                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.

                return true
            }
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item)
    }

}