package com.example.shemajamebeli5.courses

import com.example.shemajamebeli5.courses.model.ActiveCourses
import com.example.shemajamebeli5.courses.model.NewCourses

sealed class CourseState(
    val message: String = "",
    val newCourses: List<NewCourses>? = null,
    val activeCourses: List<ActiveCourses>? = null
) {
    data object Loading : CourseState()
    data class Success(val nCourses: List<NewCourses>, val actCourses: List<ActiveCourses>) : CourseState(
        newCourses = nCourses, activeCourses = actCourses)
    data class Error(val errorMessage: String) : CourseState(message = errorMessage)
}