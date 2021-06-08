package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(@PrimaryKey @SerializedName("id") val id : Int,
                @SerializedName("name") val name : String?,
                @SerializedName("username") val userName : String?,
                @SerializedName("email") val email : String?) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(userName)
        dest.writeString(email)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(source: Parcel): User {
            return User(source.readInt(),
                source.readString(), source.readString(), source.readString())
        }

        override fun newArray(size: Int): Array<User> {
            return emptyArray()
        }
    }
}