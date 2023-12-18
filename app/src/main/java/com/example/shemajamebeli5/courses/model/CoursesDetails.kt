package com.example.shemajamebeli5.courses.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CourseDetails(
    @Json(name = "new_courses") val newCourses: List<NewCourses>,
    @Json(name = "active_courses") val activeCourses: List<ActiveCourses>
)