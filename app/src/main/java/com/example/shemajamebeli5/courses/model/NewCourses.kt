package com.example.shemajamebeli5.courses.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewCourses(
    val id: Int,
    @Json(name = "icon_type")val iconType: String,
    val duration: String,
    val title: String,
    val question: String,
    @Json(name = "main_color")val mainColor: String
)


