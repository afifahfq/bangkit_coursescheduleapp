package com.dicoding.courseschedule.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    //TODO 7 : Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course

        course.apply {
            val dayName = course.day.let { getByNumber(it) }
            val timeFormat = String.format(timeString, dayName, startTime, endTime)

            val tvCourse = itemView.findViewById<TextView>(R.id.tv_course)
            tvCourse.text = courseName

            val tvTime = itemView.findViewById<TextView>(R.id.tv_time)
            tvTime.text = timeFormat

            val tvLecturer = itemView.findViewById<TextView>(R.id.tv_lecturer)
            tvLecturer.text = lecturer
        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}