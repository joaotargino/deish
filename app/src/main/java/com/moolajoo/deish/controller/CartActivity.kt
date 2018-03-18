package com.moolajoo.deish.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.moolajoo.deish.R
import com.moolajoo.deish.adapters.CartAdapter
import com.moolajoo.deish.adapters.ProductsAdapter
import com.moolajoo.deish.model.Product
import com.moolajoo.deish.util.EXTRA_CART
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_store_detail.*

class CartActivity : AppCompatActivity() {

    private var mProductsList: List<Product>? = null
    lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        mProductsList = intent.getSerializableExtra(EXTRA_CART) as List<Product>
        println(mProductsList)
        populateCart()

        fabPlaceOrder.setOnClickListener {
            createOrder()
            //get array list, start order activity
            println(mProductsList!!.toString())

            //TODO place order

        }
    }

    fun populateCart() {
        adapter = CartAdapter(this, mProductsList!!) { productItem ->
            println(productItem.name)
            Toast.makeText(this, productItem.name + " added", Toast.LENGTH_SHORT).show()

            //TODO add option to add/del
        }
        recyclerViewCart.adapter = adapter

        val layoutManager = GridLayoutManager(this, 1)
        recyclerViewCart.layoutManager = layoutManager
//        recyclerView.layoutManager.smoothScrollToPosition(recyclerView, null, viewPosition) //TODO scroll on restore
        recyclerViewCart.setHasFixedSize(true)
    }


    fun createOrder(){
        var orderItens = HashMap<String, Int>()

        for (item in mProductsList!!.iterator()){
            if(orderItens.containsKey(item.name)){
                var quantity = orderItens.get(item.name) as Int + 1
                orderItens.put(item.name, quantity)
//                orderItens[item.name] = orderItens[item.name] + 1
            }
            else orderItens[item.name] = 1
        }
        println(orderItens)
    }
}
