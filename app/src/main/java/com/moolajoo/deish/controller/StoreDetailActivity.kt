package com.moolajoo.deish.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.DisplayMetrics
import android.widget.Toast
import com.moolajoo.deish.R
import com.moolajoo.deish.adapters.ProductsAdapter
import com.moolajoo.deish.model.OrderItem
import com.moolajoo.deish.model.Product
import com.moolajoo.deish.model.Store
import com.moolajoo.deish.network.ApiClient
import com.moolajoo.deish.util.EXTRA_CART
import com.moolajoo.deish.util.EXTRA_STORE
import com.moolajoo.deish.util.EXTRA_TOKEN
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_store_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class StoreDetailActivity : AppCompatActivity() {

    private var mProductsList: List<Product>? = null
    lateinit var adapter: ProductsAdapter
    private var disposable: Disposable? = null


    private var productsOnCart: ArrayList<Product>? = ArrayList<Product>()
    private var currentOrder: ArrayList<OrderItem>? = ArrayList<OrderItem>()


    private val apiServe by lazy {
        ApiClient.create()
    }

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)

        val storeObject = intent.getParcelableExtra<Store>(EXTRA_STORE)
        token = intent.getStringExtra(EXTRA_TOKEN)

        fetchProducts(storeObject.id)

        title = storeObject.name

        fabCart.setOnClickListener {
            //get array list, start order activity
            println(productsOnCart!!.toString())

            val orderIntent = Intent(this, CartActivity::class.java)
            orderIntent.putExtra(EXTRA_CART, productsOnCart)
            orderIntent.putExtra(EXTRA_STORE, storeObject)
            orderIntent.putExtra(EXTRA_TOKEN, token)
            startActivity(orderIntent)
        }
    }


    private fun fetchProducts(id: Int) {

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

            alert(productItem.description,productItem.name) {
                positiveButton("Add to Cart") {
                    productsOnCart!!.add(productItem)
                    toast(productItem.name + " added")
                }
                negativeButton("Cancel"){
                    
                }
            }.show()
            //TODO add dialog box to confirm/cancel adding a product and/or snackbar to undo
        }
        recyclerViewProducts.adapter = adapter

        val layoutManager = GridLayoutManager(this, numberOfColumns())
        recyclerViewProducts.layoutManager = layoutManager
//        recyclerView.layoutManager.smoothScrollToPosition(recyclerView, null, viewPosition) //TODO scroll on restore
        recyclerViewProducts.setHasFixedSize(true)

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
