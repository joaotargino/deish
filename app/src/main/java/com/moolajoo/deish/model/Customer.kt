package com.moolajoo.deish.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by joaopaulotargino on 2018-03-17.
 */
class Customer (val id : Int, val email:String, val name :String, val address: String,
                val creation : String, val password : String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(email)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(creation)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun createFromParcel(parcel: Parcel): Customer {
            return Customer(parcel)
        }

        override fun newArray(size: Int): Array<Customer?> {
            return arrayOfNulls(size)
        }
    }
}


//id	integer($int64)
//email*	string
//name*	string
//address*	string
//creation	string($date-time)
//password*	string