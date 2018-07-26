package com.dadaabc.zhuozan.dadaabcstudent.model

import android.os.Parcel
import android.os.Parcelable
import android.text.SpannableStringBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by nullop on 2017/8/26.
 * Copyright  2017 peng.sun@dadaabc.com. All rights reserved.
 */

data class VoicePractise(
    var id: Int = 0,
    @SerializedName("soundIdEncrypt")
    var soundId: String? = null,
    @SerializedName("soundMp3")
    var soundMp3: String,
    @SerializedName("courseId")
    var courseId: Int = 0,
    @SerializedName("soundTxt")
    var soundTxt: String? = null,
    @SerializedName("txtCn")
    var chineseWords: String? = null,
    @SerializedName("hearNum")
    var hearTimes: Int = 0,
    @SerializedName("recordNum")
    var recordTimes: Int = 0,
    @SerializedName("currentScore")
    var currentScore: Int = 0,
    @SerializedName("highestScore")
    var highestScore: Int = 0,
    @SerializedName("scoreEncrypt")
    var score: String? = null,
    var sort: Int = 0,
    var goldCoin: Int = 0,
    @SerializedName("PracticeTimesToday")
    var practiceTimesToday: Int = 0,
    @SerializedName("updateTime")
    var updateTime: String? = null,

    var soundCoverUrl: String? = null,

    var addGoldCoin: Int = 0,

    var courseName: String? = null,

    var practiceRecordingUrl: String? = null,

    var bookCategoryId: String? = null,

    @Expose
    var isNeedShineAnimation: Boolean = false,

    @Expose
    var colorText: SpannableStringBuilder? = null,

    @Expose(serialize = false, deserialize = false)
    var isOperating: Boolean = false

) : Parcelable {

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(soundId)
        writeString(soundMp3)
        writeInt(courseId)
        writeString(soundTxt)
        writeString(chineseWords)
        writeInt(hearTimes)
        writeInt(recordTimes)
        writeInt(currentScore)
        writeInt(highestScore)
        writeString(score)
        writeInt(sort)
        writeInt(goldCoin)
        writeInt(practiceTimesToday)
        writeString(updateTime)
        writeString(soundCoverUrl)
        writeInt(addGoldCoin)
        writeString(courseName)
        writeString(practiceRecordingUrl)
        writeString(bookCategoryId)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VoicePractise> = object : Parcelable.Creator<VoicePractise> {
            override fun createFromParcel(source: Parcel): VoicePractise = VoicePractise(source)
            override fun newArray(size: Int): Array<VoicePractise?> = arrayOfNulls(size)
        }
    }
}
