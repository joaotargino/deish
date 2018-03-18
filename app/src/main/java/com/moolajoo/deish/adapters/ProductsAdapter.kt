package com.moolajoo.deish.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.moolajoo.deish.R
import com.moolajoo.deish.model.Product
import com.moolajoo.deish.util.STORE_LOGO_URL
import com.squareup.picasso.Picasso

/**
 * Created by joaopaulotargino on 2018-03-18.
 */
class ProductsAdapter(context: Context, productList: List<Product>, val itemClick: (Product) -> Unit) :
        RecyclerView.Adapter<ProductsAdapter.Holder>() {
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
                .inflate(R.layout.product_item, parent, false)
        return Holder(gridView, itemClick)
    }

    inner class Holder(itemView: View?, val itemClick: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val productLogo = itemView?.findViewById<ImageView>(R.id.ivPicture)
        val productName = itemView?.findViewById<TextView>(R.id.tvName)

        fun bind(product: Product, context: Context) {
            productName?.text = product.name
            Picasso.get()
                    .load(STORE_LOGO_URL)
                    .error(R.drawable.skip)
//                    .placeholder(R.drawable.skip)
                    .into(productLogo)

            itemView.contentDescription = product.name

            itemView.setOnClickListener {
                itemClick(product)
            }
        }

    }
}