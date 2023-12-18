package com.example.shemajamebeli5.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shemajamebeli5.AppError
import com.example.shemajamebeli5.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActiveCoursesViewModel: ViewModel() {
    private val _coursesState = MutableStateFlow<CourseState>(CourseState.Loading)
    val coursesState: StateFlow<CourseState> = _coursesState

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.coursesResponse().getCourses()
                _coursesState.value = CourseState.Success(response.newCourses, response.activeCourses)

            } catch (e: Exception) {
                val error: AppError = AppError.fromException(e)
                _coursesState.value = CourseState.Error(errorMessage = error.message)
            }
        }
    }
}