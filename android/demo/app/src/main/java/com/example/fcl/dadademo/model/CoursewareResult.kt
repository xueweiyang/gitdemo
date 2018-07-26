package com.example.fcl.dadademo.model

import com.google.gson.annotations.SerializedName

/**
 * Created by sunpeng on 03/04/2018.
 * Copyright  2018 peng.sun@dadaabc.com. All rights reserved.
 */
data class CoursewareResult(

    @SerializedName("book_id")
    var bookId: String,

    var data: List<Courseware>

)