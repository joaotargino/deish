package com.moolajoo.deish.model

import android.os.Parcel
import android.os.Parcelable


class OrderItem (val id : Int, val orderId : Int, val productId: Int,
                 val product: Product, val price : Double, val quantity : Int,
                 val total: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readParcelable(Product::class.java.classLoader),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(orderId)
        parcel.writeInt(productId)
        parcel.writeParcelable(product, flags)
        parcel.writeDouble(price)
        parcel.writeInt(quantity)
        parcel.writeDouble(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderItem> {
        override fun createFromParcel(parcel: Parcel): OrderItem {
            return OrderItem(parcel)
        }

        override fun newArray(size: Int): Array<OrderItem?> {
            return arrayOfNulls(size)
        }
    }
}


//id	integer($int64)
//orderId*	integer($int64)
//productId*	integer($int64)
//product	Product{
//    id	integer($int64)
//    storeId	integer($int64)
//    name	string
//            description	string
//            price	number($double)
//}
//price*	number($double)
//quantity*	integer($int64)
//total