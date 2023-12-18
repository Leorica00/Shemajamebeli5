package com.example.shemajamebeli5.courses

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shemajamebeli5.BaseFragment
import com.example.shemajamebeli5.courses.adapter.ActiveCoursesRecyclerAdapter
import com.example.shemajamebeli5.courses.adapter.NewCoursesRecyclerAdapter
import com.example.shemajamebeli5.databinding.FragmentCoursesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CoursesFragment : BaseFragment<FragmentCoursesBinding>(FragmentCoursesBinding::inflate) {

    private lateinit var newCoursesAdapter: NewCoursesRecyclerAdapter
    private lateinit var activeCoursesAdapter: ActiveCoursesRecyclerAdapter

    private val coursesViewModel: ActiveCoursesViewModel by viewModels()

    override fun setUp() {
        setUpRecyclers()
    }

    private fun setUpRecyclers() {
        with(binding.recyclerNewCourses) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = NewCoursesRecyclerAdapter().apply {
                newCoursesAdapter = this
            }
        }

        with(binding.recyclerActiveCourses) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ActiveCoursesRecyclerAdapter().apply {
                activeCoursesAdapter = this
            }
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                coursesViewModel.coursesState.collectLatest {
                    handleResource(it)
                }
            }
        }
    }

    private fun handleResource(resource: CourseState) {
        when (resource) {
            is CourseState.Loading -> {
                binding.progressBarCourses.visibility = View.VISIBLE
            }

            is CourseState.Success -> {
                newCoursesAdapter.submitList(resource.newCourses)
                activeCoursesAdapter.submitList(resource.actCourses)
                binding.progressBarCourses.visibility = View.GONE
            }

            is CourseState.Error -> {
                Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
                binding.progressBarCourses.visibility = View.GONE
            }
        }
    }



}