package com.example.fcl.dadademo.model

import com.google.gson.annotations.SerializedName

/**
 * Created by sunpeng on 30/03/2018.
 * Copyright  2018 peng.sun@dadaabc.com. All rights reserved.
 */
data class Courseware(

    @SerializedName("id_encrypt")
    var courseId: String,

    @SerializedName("course_name")
    var courseName: String,

    @SerializedName("course_name_cn")
    var courseNameCn: String,

    @SerializedName("course_img")
    var courseImage: String,

    @SerializedName("sound_total")
    var soundTotal: String,

    @SerializedName("sound_finish")
    var soundFinish: String,

    @SerializedName("course_lock")
    var courseLock: Int
)
