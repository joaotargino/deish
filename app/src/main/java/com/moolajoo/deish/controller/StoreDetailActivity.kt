package com.moolajoo.deish.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.DisplayMetrics
import android.widget.Toast
import com.moolajoo.deish.R
import com.moolajoo.deish.adapters.ProductsAdapter
import com.moolajoo.deish.model.Product
import com.moolajoo.deish.model.Store
import com.moolajoo.deish.network.ApiClient
import com.moolajoo.deish.util.EXTRA_STORE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class StoreDetailActivity : AppCompatActivity() {

    private var mProductsList: List<Product>? = null
    lateinit var adapter: ProductsAdapter
    private var disposable: Disposable? = null

    private val apiServe by lazy {
        ApiClient.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)

        val storeObject = intent.getParcelableExtra<Store>(EXTRA_STORE)

        fetchProducts(storeObject.id)
    }


    private fun fetchProducts(id : Int) {

        disposable =
                apiServe.getStoreProducts(id.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    mProductsList = result
                                    populateProductsList()
                                },
                                { error ->
                                    println(error.message)
                                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                                }
                        )
    }

    private fun populateProductsList() {

        adapter = ProductsAdapter(this, mProductsList!!) { productItem ->
            println(productItem.name)
//            val storesIntent = Intent(this, StoreDetailActivity::class.java)
//            storesIntent.putExtra(EXTRA_STORE, productItem)
//            startActivity(productItem)
        }
        recyclerView.adapter = adapter

        val layoutManager = GridLayoutManager(this, numberOfColumns())
        recyclerView.layoutManager = layoutManager
//        recyclerView.layoutManager.smoothScrollToPosition(recyclerView, null, viewPosition) //TODO scroll on restore
        recyclerView.setHasFixedSize(true)

    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {

        } catch (e: Exception) {
            println(e.message)
        }

    }

    private fun numberOfColumns(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthDivider = 600
        val width = displayMetrics.widthPixels
        val nColumns = width / widthDivider
        return if (nColumns < 2) 2 else nColumns
    }

}
