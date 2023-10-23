package com.example.oquvmarkaz.database

import com.example.oquvmarkaz.models.CourseData

interface DatabaseServer {

    fun addCourse(courseData: CourseData)
    fun listCourse():List<CourseData>

    fun editCourse(courseData: CourseData)
    fun deleteCourse(courseData: CourseData)
    fun getCourseCount():Int

   // fun getCourseById(id:Int):CourseData
}