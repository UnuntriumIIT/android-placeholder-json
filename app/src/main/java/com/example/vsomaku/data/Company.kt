package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Company(@SerializedName("name") val name : String?,
              @SerializedName("catchPhrase") val phrase : String?) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(phrase)
    }

    companion object CREATOR : Parcelable.Creator<Company> {
        override fun createFromParcel(source: Parcel): Company {
            return Company(source.readString(), source.readString())
        }

        override fun newArray(size: Int): Array<Company> {
            return emptyArray()
        }
    }
}