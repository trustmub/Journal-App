package com.trustathanas.journalapp.models

import android.os.Parcel
import android.os.Parcelable

class Journal(val record_id: Int, val title: String, val contents: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(record_id)
        parcel.writeString(title)
        parcel.writeString(contents)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Journal> {
        override fun createFromParcel(parcel: Parcel): Journal {
            return Journal(parcel)
        }

        override fun newArray(size: Int): Array<Journal?> {
            return arrayOfNulls(size)
        }
    }
}