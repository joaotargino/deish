package com.moolajoo.deish.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.moolajoo.deish.R
import com.moolajoo.deish.model.Order

/**
 * Created by joaopaulotargino on 2018-03-18.
 */
class OrdersAdapter(context: Context, orderList: List<Order>, val itemClick: (Order) -> Unit) :
        RecyclerView.Adapter<OrdersAdapter.Holder>() {
    val context = context
    val mOrderList = orderList

    override fun getItemCount(): Int {
        return mOrderList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(mOrderList[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val gridView = LayoutInflater.from(context)
                .inflate(R.layout.order_item, parent, false)
        return Holder(gridView, itemClick)
    }

    inner class Holder(itemView: View?, val itemClick: (Order) -> Unit) : RecyclerView.ViewHolder(itemView) {

        //TODO list and group by quantity
        val orderContact = itemView?.findViewById<TextView>(R.id.tvOrderContact)
        val orderTotal = itemView?.findViewById<TextView>(R.id.tvOrderTotal)
        val orderStatus = itemView?.findViewById<TextView>(R.id.tvOrderStatus)

        fun bind(order: Order, context: Context) {

            orderContact?.text = "Contact: " + order.contact
            orderStatus?.text = "Status: " + order.status
            orderTotal?.text = "$: " + order.total.toString()


            itemView.contentDescription = order.contact

            itemView.setOnClickListener {
                itemClick(order)
            }
        }

    }
}
