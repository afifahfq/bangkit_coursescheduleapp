package com.dicoding.courseschedule.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.courseschedule.util.*
import java.util.*

//TODO 4 : Implement repository with appropriate dao
class DataRepository(private val dao: CourseDao) {
    fun getNearestSchedule(queryType: QueryType) : LiveData<Course?> {
        val query = QueryUtil.nearestQuery(queryType)
        val courses = dao.getNearestSchedule(query)

        return courses
//        throw NotImplementedError("needs implementation")
    }

    fun getAllCourse(sortType: SortType): LiveData<PagedList<Course>> {
        val query = QueryUtil.sortedQuery(sortType)
        val courses = dao.getAll(query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(courses, config).build()
//        throw NotImplementedError("needs implementation")
    }

    fun getCourse(id: Int) : LiveData<Course> {
        return dao.getCourse(id)
//        throw NotImplementedError("needs implementation")
    }

    fun getTodaySchedule() : List<Course> {
        //TODO 99 : diff
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]

        return dao.getTodaySchedule(day)
//        throw NotImplementedError("needs implementation")
    }

    fun insert(course: Course) = executeThread {
        dao.insert(course)
    }

    fun delete(course: Course) = executeThread {
        dao.delete(course)
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        private const val PAGE_SIZE = 10

        fun getInstance(context: Context): DataRepository? {
            return instance ?: synchronized(DataRepository::class.java) {
                if (instance == null) {
                    val database = CourseDatabase.getInstance(context)
                    instance = DataRepository(database.courseDao())
                }
                return instance
            }
        }
    }
}