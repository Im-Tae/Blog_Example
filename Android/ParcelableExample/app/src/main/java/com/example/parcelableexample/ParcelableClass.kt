package com.example.parcelableexample

import android.os.Parcel
import android.os.Parcelable

class ParcelableClass : Parcelable {

    var number: Int = 0
    var name: String? = null

    companion object {
        @JvmField
        val CREATOR : Parcelable.Creator<ParcelableClass> = object : Parcelable.Creator<ParcelableClass> {

            override fun createFromParcel(source: Parcel?): ParcelableClass {
                val data = ParcelableClass()
                data.number = source?.readInt()!!
                data.name = source.readString()

                return data
            }

            override fun newArray(size: Int): Array<ParcelableClass?> {
                return arrayOfNulls<ParcelableClass>(size)
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(number)
        dest?.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }
}