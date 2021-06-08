package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Address(@SerializedName("street") val street : String?,
                   @SerializedName("suite") val apartment : String?,
                   @SerializedName("city") val city : String?) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(street)
        dest.writeString(apartment)
        dest.writeString(city)
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(source: Parcel): Address {
            return Address(source.readString(), source.readString(), source.readString())
        }

        override fun newArray(size: Int): Array<Address> {
            return emptyArray()
        }
    }
}