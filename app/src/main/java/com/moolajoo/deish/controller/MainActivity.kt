package com.moolajoo.deish.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.DisplayMetrics
import android.widget.Toast
import com.moolajoo.deish.R
import com.moolajoo.deish.adapters.StoresAdapter
import com.moolajoo.deish.model.Store
import com.moolajoo.deish.model.StoreResponse
import com.moolajoo.deish.network.ApiClient
import com.moolajoo.deish.util.EXTRA_ORDER
import com.moolajoo.deish.util.EXTRA_STORE
import com.moolajoo.deish.util.RESPONSE
import com.moolajoo.deish.util.TOKEN
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mStoresList: List<Store>? = null
    private var storesResponse: StoreResponse? = null
    lateinit var adapter: StoresAdapter
    private val apiServe by lazy {
        ApiClient.create()
    }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //wip save/restore
        fetchStores()

        fabViewOrders.setOnClickListener {
            val viewOrdersIntent = Intent(this, ViewOrdersActivity::class.java)
            viewOrdersIntent.putExtra(EXTRA_ORDER, TOKEN)
            startActivity(viewOrdersIntent)
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(RESPONSE, storesResponse)
        //wip
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        storesResponse = savedInstanceState!!.getParcelable(RESPONSE)

    }

    private fun fetchStores() {

        disposable =
                apiServe.getStoresList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    mStoresList = result
                                    populateStoresList()
                                },
                                { error ->
                                    println(error.message)
                                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                                }
                        )
    }


    private fun numberOfColumns(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthDivider = 300
        val width = displayMetrics.widthPixels
        val nColumns = width / widthDivider
        return if (nColumns < 2) 2 else nColumns
    }


    private fun populateStoresList() {

        adapter = StoresAdapter(this, mStoresList!!) { storeItem ->
            val storesIntent = Intent(this, StoreDetailActivity::class.java)
            storesIntent.putExtra(EXTRA_STORE, storeItem)
            startActivity(storesIntent)
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

}

