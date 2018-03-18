package com.moolajoo.deish.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.moolajoo.deish.R
import com.moolajoo.deish.adapters.CartAdapter
import com.moolajoo.deish.model.Order
import com.moolajoo.deish.model.OrderItem
import com.moolajoo.deish.model.Product
import com.moolajoo.deish.model.Store
import com.moolajoo.deish.util.EXTRA_CART
import com.moolajoo.deish.util.EXTRA_STORE
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private var mProductsList: List<Product>? = null
    private var mOrder: ArrayList<OrderItem>? = ArrayList<OrderItem>()
    lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        mProductsList = intent.getSerializableExtra(EXTRA_CART) as List<Product>
        val storeObject = intent.getParcelableExtra<Store>(EXTRA_STORE)

        println(storeObject.name)
        println(mProductsList)
        populateCart()
        createOrder()

        val customerID = 0 //TODO get the customer!!!
        val address = "Address"
        val contact = "99998 0605"
        val storeId = storeObject.id
        val status = "processing"
        val lastUpdate = "date"


        fabPlaceOrder.setOnClickListener {
            //get array list, start order activity
            println(mOrder!!)

            val theOrder = Order(424242, "date", customerID, address, contact, storeId,
                    mOrder!!, mOrder!!.get(0).total, status, lastUpdate)

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


    fun createOrder() {
        var orderMap = HashMap<String, Int>()
        var orderItens = HashMap<String, OrderItem>()

        val id = 4242
        val orderId = 4242
        val qtd = 1
        var total: Double = 0.0

        for (item in mProductsList!!.iterator()) {
            if (orderMap.containsKey(item.name)) {

                total += item.price
                println(total)
                val quantity = orderMap.get(item.name) as Int + 1
                orderMap.put(item.name, quantity)
                orderItens.put(item.name, OrderItem(id, orderId, item.id, item,
                        item.price * quantity, quantity, total))
//                orderItens[item.name] = orderItens[item.name] + 1
            } else {
                total += item.price
                orderMap[item.name] = 1
                orderItens[item.name] = OrderItem(id, orderId, item.id, item, item.price, qtd, total)
                println(total)
            }
        }
        println(orderItens)
        for (p in orderItens.iterator()) {
            mOrder!!.add(p.value)
//            println(p.value.product.name)
//            println(p.value.quantity)
//            println(p.value.price)
//            println(p.value.total)
        }
        val totalString = "Total: $ %.2f".format(total)
        tvTotalValue.text = totalString

    }
}
