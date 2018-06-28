package com.trustathanas.journalapp.models

import android.os.Parcel
import android.os.Parcelable

class LoginCredentialParcelable(val displayName: String, val email: String, val familyName: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(displayName)
        parcel.writeString(email)
        parcel.writeString(familyName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginCredentialParcelable> {
        override fun createFromParcel(parcel: Parcel): LoginCredentialParcelable {
            return LoginCredentialParcelable(parcel)
        }

        override fun newArray(size: Int): Array<LoginCredentialParcelable?> {
            return arrayOfNulls(size)
        }
    }
}