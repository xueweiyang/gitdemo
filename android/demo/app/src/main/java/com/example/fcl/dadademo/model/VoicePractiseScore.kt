package com.dadaabc.zhuozan.dadaabcstudent.model

import com.google.gson.annotations.SerializedName

/**
 * Created by sunpeng on 2018/5/15.
 * Copyright  2018 peng.sun@dadaabc.com. All rights reserved.
 */
data class VoicePractiseScore(

    var id: Int = 0,

    @SerializedName("score")
    var currentScore: Int = 0,

    @SerializedName("practice_info")
    var scoreEncrypt: String?

)
