package com.moolajoo.deish.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.moolajoo.deish.R
import com.moolajoo.deish.model.Store
import com.moolajoo.deish.util.STORE_LOGO_URL
import com.squareup.picasso.Picasso

/**
 * Created by joaopaulotargino on 2018-03-17.
 */
class StoresAdapter(context: Context, storeList: List<Store>, val itemClick: (Store) -> Unit) :
        RecyclerView.Adapter<StoresAdapter.Holder>() {
    val context = context
    val mStoreList = storeList

    override fun getItemCount(): Int {
        return mStoreList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(mStoreList[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val gridView = LayoutInflater.from(context)
                .inflate(R.layout.store_item, parent, false)
        return Holder(gridView, itemClick)
    }

    inner class Holder(itemView: View?, val itemClick: (Store) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val storeLogo = itemView?.findViewById<ImageView>(R.id.ivPicture)
        val storeName = itemView?.findViewById<TextView>(R.id.tvName)
        val storeRating = itemView?.findViewById<TextView>(R.id.tvCousineID)

        fun bind(store: Store, context: Context) {
            storeName?.text = store.name
            storeRating?.text = store.cousineID.toString()
            Picasso.get()
                    .load(STORE_LOGO_URL)
                    .error(R.drawable.skip)
//                    .placeholder(R.drawable.skip)
                    .into(storeLogo)

            itemView.contentDescription = store.name

            itemView.setOnClickListener { itemClick(store) }
        }

    }
}