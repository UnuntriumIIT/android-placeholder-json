package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Post(@SerializedName("userId") val userId : Int,
                @PrimaryKey @SerializedName("id") val id : Int,
                @SerializedName("title") var title : String?,
                @SerializedName("body") var description : String?) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(userId)
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeString(description)
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(source: Parcel): Post {
            return Post(
                source.readInt(), source.readInt(),
                source.readString(),
                source.readString()
            )
        }

        override fun newArray(size: Int): Array<Post> {
            return emptyArray()
        }
    }
}