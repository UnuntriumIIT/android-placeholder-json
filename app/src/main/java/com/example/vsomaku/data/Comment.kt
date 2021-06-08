package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Comment(@SerializedName("postId") val postId : Int,
                   @PrimaryKey @SerializedName("id") val id : Int,
                   @SerializedName("name") var name : String?,
                   @SerializedName("email") val email : String?,
                   @SerializedName("body") var text : String?) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(postId)
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(email)
        dest.writeString(text)
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(source: Parcel): Comment {
            return Comment(
                source.readInt(), source.readInt(),
                source.readString(), source.readString(), source.readString()
            )
        }

        override fun newArray(size: Int): Array<Comment> {
            return emptyArray()
        }

    }
}