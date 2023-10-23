package com.example.oquvmarkaz.database

import com.example.oquvmarkaz.models.CourseData
import com.example.oquvmarkaz.models.GroupData

interface DatabaseServer2 {

    fun addGroup(groupData: GroupData)
    fun listGroup(coursename: String):List<GroupData>

    fun editGroup(groupData: GroupData)
    fun deleteGroup(groupData: GroupData)
    fun getGroupCount():Int

   // fun getCourseById(id:Int):CourseData
}