package com.example.fcl.kotlindemo.model

data class Result(
    val id: Long,
    val name: String,
    val full_name: String,
    val owner: ResultOwner,
    val html_url: String,
    val description: String?,
    val url: String,
    val created_at: String,
    val updated_at: String,
    val pushed_at: String,
    val homepage: String?,
    val stargazers_count: Long,
    val watchers_count: Long,
    val watchers: String,
    val score: Double
)