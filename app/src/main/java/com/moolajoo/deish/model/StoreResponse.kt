package com.moolajoo.deish.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by joaopaulotargino on 2018-03-17.
 */
class StoreResponse() : Parcelable {

    var data: List<Store>? = null

    constructor(parcel: Parcel) : this() {
        data = parcel.createTypedArrayList(Store)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoreResponse> {
        override fun createFromParcel(parcel: Parcel): StoreResponse {
            return StoreResponse(parcel)
        }

        override fun newArray(size: Int): Array<StoreResponse?> {
            return arrayOfNulls(size)
        }
    }

}