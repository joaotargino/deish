package com.moolajoo.deish.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*


class Order(val id: Int, val date: String, val customerId: Int, val deliveryAddress: String,
            val contact: String, val storeId: Int, val orderItens: ArrayList<OrderItem>,
            val total: Double, val status: String, val lastUpdate: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
//            TODO("date"),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readArrayList() as ArrayList<OrderItem>,
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date)
        parcel.writeInt(customerId)
        parcel.writeString(deliveryAddress)
        parcel.writeString(contact)
        parcel.writeInt(storeId)
        parcel.writeArray(orderItens)
        parcel.writeDouble(total)
        parcel.writeString(status)
        parcel.writeString(lastUpdate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }

}

private fun Any.readArrayList(): ArrayList<OrderItem>? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}


private fun Parcel.writeArray(orderItens: ArrayList<OrderItem>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}
