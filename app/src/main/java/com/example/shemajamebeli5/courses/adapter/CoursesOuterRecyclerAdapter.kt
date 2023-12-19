package com.example.shemajamebeli5.courses.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shemajamebeli5.courses.model.CourseItem
import com.example.shemajamebeli5.databinding.RecyclerViewActiveCoursesLayoutBinding
import com.example.shemajamebeli5.databinding.RecyclerViewNewCoursesLayoutBinding

class CoursesOuterRecyclerAdapter : ListAdapter<CourseItem, ViewHolder>(myItemDiffCallback) {

    companion object {
        val myItemDiffCallback = object : DiffUtil.ItemCallback<CourseItem>() {
            override fun areItemsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
                return oldItem == newItem
            }
        }
        const val NEW_COURSE_VIEW = 1
        const val ACTIVE_COURSE_VIEW = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            NEW_COURSE_VIEW -> {
                InnerNewCourseViewHolder(
                    RecyclerViewNewCoursesLayoutBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }

            ACTIVE_COURSE_VIEW -> {
                InnerActiveCourseViewHolder(
                    RecyclerViewActiveCoursesLayoutBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is InnerActiveCourseViewHolder -> holder.bind(CourseItem.ActiveCourse(item.activeCourses!!))
            is InnerNewCourseViewHolder -> holder.bind(CourseItem.NewCourse(item.newCourses!!))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            getItem(position) is CourseItem.ActiveCourse -> ACTIVE_COURSE_VIEW
            getItem(position) is CourseItem.NewCourse -> NEW_COURSE_VIEW
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    inner class InnerNewCourseViewHolder(private val binding: RecyclerViewNewCoursesLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(newCourses: CourseItem.NewCourse) {
            binding.recyclerNewCourses.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = NewCoursesRecyclerAdapter().also {
                    it.submitList(newCourses.nCourses)
                }
            }
        }
    }

    inner class InnerActiveCourseViewHolder(private val binding: RecyclerViewActiveCoursesLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(activeCourses: CourseItem.ActiveCourse) {
            binding.recyclerActiveCourses.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
                adapter = ActiveCoursesRecyclerAdapter().also {
                    it.submitList(activeCourses.aCourses)
                }
            }
        }
    }


}