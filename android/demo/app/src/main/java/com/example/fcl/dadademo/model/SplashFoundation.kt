package com.example.fcl.dadademo.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.SerializedName

data class SplashFoundation(
    @SerializedName("list")
    val adList:List<SplashAd>
)

data class SplashAd(
    @SerializedName("photo")
    val imageUrl:String,
    @SerializedName("start_time")
    val startTime:Int,
    @SerializedName("end_time")
    val endTime:Int,
    @SerializedName("video_cover_url")
    val videoCoverUrl:String,
    @SerializedName("url")
    val navigationUrl:String,
    @SerializedName("event_type")
    val eventType:Int
) :Parcelable{
    constructor(parcel:Parcel) :this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeInt(startTime)
        parcel.writeInt(endTime)
        parcel.writeString(videoCoverUrl)
        parcel.writeString(navigationUrl)
        parcel.writeInt(eventType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<SplashAd> {
        override fun createFromParcel(parcel: Parcel): SplashAd {
            return SplashAd(parcel)
        }

        override fun newArray(size: Int): Array<SplashAd?> {
            return arrayOfNulls(size)
        }
    }
}