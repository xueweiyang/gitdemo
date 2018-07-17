package com.example.fcl.dadademo.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.SerializedName

/**
 * Created by Dio_V on 207/8/30.
 * Copyright © 207 diov.github.io. All rights reserved.
 */
data class HomeFoundation(
    @SerializedName("alerts")
    val emergencyNotices: List<EmergencyNotice>?,
    @SerializedName("participation")
    val trialLesson: TrialLesson?,
    @SerializedName("launch_ads")
    val dailyAds: ArrayList<Ad>,
    @SerializedName("slider_ads")
    val bannerAds: List<Ad>,
    @SerializedName("free_practice")
    val freePractice: FreePractice?,
    @SerializedName("moment")
    val daTalentShow: DaTalentShow?,
    @SerializedName("introduce")
    val introduce: Introduce<IntroduceItem>?,
    @SerializedName("live_courses")
    val liveCourses: FoundationModel<LiveCourse>?,
    @SerializedName("dada_show")
    val dadaTv: FoundationModel<DadaTvItem>?,
    @SerializedName("open_courses")
    val openCourses: FoundationModel<OpenCourse>?,
    @SerializedName("one2one_courses")
    val personalCourse: FoundationModel<PersonalCourse>?,
    @SerializedName("message_count")
    val unreadNoticeCount: Int
)

data class FoundationModel<out T>(
    val title: String?,
    val items: List<T>,
    @SerializedName("jump_url")
    val moreDataUrl: String
)

data class EmergencyNotice(
    val icon: String,
    val text: String,
    @SerializedName("jump_url")
    val navigationUrl: String,
    val level: Int
)

data class TrialLesson(
    val number: Int,
    @SerializedName("advert_content")
    val advertContent: String
)

data class FreePractice(
    val title: String,
    val items: List<FreePracticeItem>
)

data class FreePracticeItem(
    @SerializedName("practice_name")
    val itemName: String,
    @SerializedName("jump_url")
    val jumpUrl: String
)

data class Introduce<out T>(
    val title: String,
    val items: List<T>
)

data class DaTalentShow(
    val title: String,
    @SerializedName("new_likes")
    val newLikes: Int,
    @SerializedName("cover_url")
    val picUrl: String
)

data class IntroduceItem(
    @SerializedName("cover_url")
    val picUrl: String,
    val title: String,
    val desc: String,
    @SerializedName("vod_url")
    val videoUrl: String
)

data class DadaTvItem(
    @SerializedName("font_cover")
    val picUrl: String,
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("c_time")
    val time: Long,
    @SerializedName("c_time_msg")
    val timeMsg: String?,
    @SerializedName("t_name")
    val teacherName: String,
    @SerializedName("t_logo")
    val logoUrl: String,
    @SerializedName("remind_number")
    val remindNumber: Int,
    @SerializedName("remind_msg")
    val remindMsg: String?,
    @SerializedName("jump_url")
    val jumpUrl: String
)

data class Ad(
    val id: Int,
    val name: String,
    @SerializedName("photo")
    val imageUrl: String,
    @SerializedName("url")
    val navigationUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(imageUrl)
        parcel.writeString(navigationUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Ad> {
        override fun createFromParcel(parcel: Parcel): Ad {
            return Ad(parcel)
        }

        override fun newArray(size: Int): Array<Ad?> {
            return arrayOfNulls(size)
        }
    }
}

data class LiveCourse(
    @SerializedName("font_cover")
    val coverUrl: String,
    @SerializedName("course_name")
    val title: String,
    @SerializedName("c_time")
    val startTime: Long,
    @SerializedName("run_status")
    val status: Int,
    @SerializedName("level")
    val tag: String?,
    @SerializedName("t_name")
    val teacherName: String,
    @SerializedName("t_logo")
    val avatarUrl: String,
    @SerializedName("is_free")
    val isFree: Int,
    @SerializedName("is_remind")
    val willRemind: Int,
    @SerializedName("remind_number")
    val remindNumber: Int,
    @SerializedName("jump_url")
    val navigationUrl: String
) {
    fun isFree(): Boolean {
        return isFree == 1
    }

    companion object {
        const val CLASS_NOT_BEGIN = 1
        const val CLASS_FINISHED = 2
        const val CLASS_PROCESSING = 3
    }
}

data class OpenCourse(
    @SerializedName("course_id")
    val id: Int,
    @SerializedName("font_cover")
    val coverUrl: String,
    @SerializedName("course_name")
    val title: String,
    @SerializedName("course_desc")
    val description: String,
    @SerializedName("jump_url")
    val navigationUrl: String,
    @SerializedName("level")
    val level: List<String>,
    @SerializedName("watch_times")
    val playCount: String
)

data class PersonalCourse(
    val id: Int,
    @SerializedName("start_time")
    val startTime: Long,
    @SerializedName("c_name_cn")
    val chineseName: String,
    @SerializedName("c_name_en")
    val englishName: String,
    @SerializedName("teacher_avatar")
    val teacherAvatar: String,
    /**
     * 课程状态
     */
    val status: Int,
    /**
     * 课程预约类型
     */
    @SerializedName("appro_status")
    val approStatus: Int,
    @SerializedName("course_type")
    val type: String,
    @SerializedName("jump_url")
    val navigationUrl: String
)
