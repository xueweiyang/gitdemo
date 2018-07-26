package com.example.fcl.dadademo.model

import com.google.gson.annotations.SerializedName

/**
 * Created by 王天明 on 2018/7/18.
 */
data class VoicePracticeErrorModel(
    val title: String,
    val list: List<VoicePracticeErrorDetails>
)

data class VoicePracticeErrorDetails(
    @SerializedName("reason_id")
    val id: Int,
    @SerializedName("reason_content")
    val reasonContent: String
)
