package com.example.shemajamebeli5.courses.model

import com.example.shemajamebeli5.courses.CourseState.Loading.newCourses

sealed class CourseItem(
    val newCourses: List<NewCourses>? = null,
    val activeCourses: List<ActiveCourses>? = null
) {
    data class NewCourse(val nCourses: List<NewCourses>) : CourseItem(newCourses = nCourses)
    data class ActiveCourse(val aCourses: List<ActiveCourses>) : CourseItem(activeCourses = aCourses)
}
