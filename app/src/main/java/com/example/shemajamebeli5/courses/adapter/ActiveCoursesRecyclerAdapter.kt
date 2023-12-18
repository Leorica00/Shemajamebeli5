package com.example.shemajamebeli5.courses.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shemajamebeli5.R
import com.example.shemajamebeli5.courses.model.ActiveCourses
import com.example.shemajamebeli5.databinding.ActiveCoursesRecyclerItemBinding

class ActiveCoursesRecyclerAdapter :
    androidx.recyclerview.widget.ListAdapter<ActiveCourses, ActiveCoursesRecyclerAdapter.ActiveCoursesViewHolder>(
        myItemDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveCoursesViewHolder {
        return ActiveCoursesViewHolder(
            ActiveCoursesRecyclerItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ActiveCoursesViewHolder, position: Int) {
        holder.bind()
    }

    inner class ActiveCoursesViewHolder(private val binding: ActiveCoursesRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val activeCourse = currentList[bindingAdapterPosition]
            val colorInt = Color.parseColor("#${activeCourse.mainColor}")
            val opacity = activeCourse.backgroundColorPresent * 255 / 100
            val btnOpacity = activeCourse.playButtonColorPresent * 255 / 100
            val opacityColor = Color.argb(opacity, Color.red(colorInt), Color.green(colorInt), Color.blue(colorInt))
            val btnOpacityColor = Color.argb(btnOpacity, Color.red(colorInt), Color.green(colorInt), Color.blue(colorInt))

            with(binding) {
                Glide.with(itemView.context).load(activeCourse.image).into(binding.ivImage)

                tvBookingTimeUpper.text = activeCourse.title
                tvBookingTimeUpper.setTextColor(colorInt)

                tvBookingTimeDown.text = "Booked for ${activeCourse.bookingTime}"
                tvBookingTimeDown.setTextColor(colorInt)

                ImageViewCompat.setImageTintList(binding.ivPlay, ColorStateList.valueOf(colorInt))
                val playButtonDrawable =
                    ContextCompat.getDrawable(root.context, R.drawable.costume_round_background)

                (playButtonDrawable as? GradientDrawable)?.let { gradientDrawable ->
                    gradientDrawable.setColor(btnOpacityColor)
                    ivPlay.background = gradientDrawable
                }

                root.setBackgroundColor(colorInt)
                val shapeDrawable = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.costume_active_course_background
                ) as GradientDrawable
                shapeDrawable.setColor(opacityColor)
                root.background = shapeDrawable

                progressBar.progress = activeCourse.progress.toInt()
            }
        }
    }

    companion object {
        val myItemDiffCallback = object : DiffUtil.ItemCallback<ActiveCourses>() {

            override fun areItemsTheSame(oldItem: ActiveCourses, newItem: ActiveCourses): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ActiveCourses,
                newItem: ActiveCourses
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}