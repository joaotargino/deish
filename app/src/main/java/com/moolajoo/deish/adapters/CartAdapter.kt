package com.moolajoo.deish.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.moolajoo.deish.R
import com.moolajoo.deish.model.Product

/**
 * Created by joaopaulotargino on 2018-03-18.
 */
class CartAdapter(context: Context, productList: List<Product>, val itemClick: (Product) -> Unit) :
        RecyclerView.Adapter<CartAdapter.Holder>() {
    val context = context
    val mProductList = productList

    override fun getItemCount(): Int {
        return mProductList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(mProductList[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val gridView = LayoutInflater.from(context)
                .inflate(R.layout.cart_item, parent, false)
        return Holder(gridView, itemClick)
    }

    inner class Holder(itemView: View?, val itemClick: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {

        //TODO list and group by quantity
        val productQuantity = itemView?.findViewById<TextView>(R.id.tvItemQuantity)
        val productName = itemView?.findViewById<TextView>(R.id.tvItemName)

        fun bind(product: Product, context: Context) {

            productName?.text = product.name
            productQuantity?.text = product.price.toString()


            itemView.contentDescription = product.name

            itemView.setOnClickListener {
                itemClick(product)
            }
        }

    }
}
