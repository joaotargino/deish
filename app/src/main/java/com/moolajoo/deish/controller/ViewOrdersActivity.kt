package com.moolajoo.deish.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.moolajoo.deish.R
import com.moolajoo.deish.adapters.CartAdapter
import com.moolajoo.deish.adapters.OrdersAdapter
import com.moolajoo.deish.model.Order
import com.moolajoo.deish.model.OrderItem
import com.moolajoo.deish.network.ApiClient
import com.moolajoo.deish.util.TOKEN
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_view_orders.*

class ViewOrdersActivity : AppCompatActivity() {


    private var mOrderList: List<Order>? = null
    private var mOrder: ArrayList<OrderItem>? = ArrayList<OrderItem>()
    lateinit var adapter: OrdersAdapter
    private var disposable: Disposable? = null

    private val apiServe by lazy {
        ApiClient.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_orders)

        fetchOrders()
    }

    fun fetchOrders(){
        disposable =
                    apiServe.getOrder(TOKEN, "application/json")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { result ->
                                        mOrderList = result
                                        println(result)
                                        populateOrders()
                                    },
                                    { error ->
                                        println(error.message)
                                        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                                    }
                            )

    }
    fun populateOrders() {

        adapter = OrdersAdapter(this, mOrderList!!) { orderItem ->
            Toast.makeText(this, orderItem.contact + " added", Toast.LENGTH_SHORT).show()

        }
        recyclerViewOrder.adapter = adapter

        val layoutManager = GridLayoutManager(this, 1)
        recyclerViewOrder.layoutManager = layoutManager
//        recyclerView.layoutManager.smoothScrollToPosition(recyclerView, null, viewPosition) //TODO scroll on restore
        recyclerViewOrder.setHasFixedSize(true)
    }
}
