package com.example.shemajamebeli5.courses.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shemajamebeli5.R
import com.example.shemajamebeli5.databinding.NewCoursesRecyclerItemBinding
import com.example.shemajamebeli5.courses.model.NewCourses

class NewCoursesRecyclerAdapter: androidx.recyclerview.widget.ListAdapter<NewCourses, NewCoursesRecyclerAdapter.NewCoursesViewHolder>(
    myItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCoursesViewHolder {
        return NewCoursesViewHolder(NewCoursesRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewCoursesViewHolder, position: Int) {
        holder.bind()
    }

    inner class NewCoursesViewHolder(private val binding: NewCoursesRecyclerItemBinding): ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() = with(binding) {
            val course = getItem(bindingAdapterPosition)
            tvTitle.text = course.title
            tvQuestion.text = course.question
            tvTimeNew.text = "${course.duration} min"
            val shapeDrawable = ContextCompat.getDrawable(itemView.context, R.drawable.costume_new_course_background) as GradientDrawable
            shapeDrawable.setColor(Color.parseColor("#${course.mainColor}"))
            root.background = shapeDrawable

            if (course.iconType == "settings") {
                ivIcon.setImageResource(R.drawable.ic_settings)
            } else {
                ivIcon.setImageResource(R.drawable.ic_card)
            }
        }
    }

    companion object {
        val myItemDiffCallback = object : DiffUtil.ItemCallback<NewCourses>() {

            override fun areItemsTheSame(oldItem: NewCourses, newItem: NewCourses): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewCourses, newItem: NewCourses): Boolean {
                return oldItem == newItem
            }
        }
    }
}