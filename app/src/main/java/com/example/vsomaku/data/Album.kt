package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Album(@SerializedName("userId") val userId : Int,
            @PrimaryKey @SerializedName("id") val id : Int) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(userId)
        dest.writeInt(id)
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(source: Parcel): Album {
            return Album(source.readInt(), source.readInt())
        }

        override fun newArray(size: Int): Array<Album> {
            return emptyArray()
        }
    }
}