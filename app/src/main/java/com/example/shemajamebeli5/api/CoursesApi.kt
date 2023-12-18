package com.example.shemajamebeli5.api

import com.example.shemajamebeli5.courses.model.CourseDetails
import retrofit2.http.GET

interface CoursesApi {
    @GET("v3/83160a49-fe85-46ba-bcf8-3cf5aa09f92b")
    suspend fun getCourses(): CourseDetails}