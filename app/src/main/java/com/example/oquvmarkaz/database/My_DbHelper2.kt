package com.example.oquvmarkaz.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.oquvmarkaz.models.CourseData
import com.example.oquvmarkaz.models.GroupData

@Suppress("UNREACHABLE_CODE")
class My_DbHelper2(context: Context) : SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
), DatabaseServer2 {
    companion object {
        const val DB_NAME = "oquv markaz GURUHLAR"
        const val DB_VERSION = 1

        const val TABLE_NAME = "guruglar"
        const val GURUH_ID = "id"
        const val GURUH_NAME = "name"
        const val COURSE_NAME = "course"
        const val GURUH_IMAGE = "image"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val query =
            "create table $TABLE_NAME($GURUH_ID integer not null primary key autoincrement ,$GURUH_NAME text not null,$COURSE_NAME text not null,$GURUH_IMAGE integer not null)"
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    override fun addGroup(groupData: GroupData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GURUH_NAME, groupData.name)
        contentValues.put(COURSE_NAME, groupData.coursename)
        contentValues.put(GURUH_IMAGE, groupData.imageRes)
        database.insert(TABLE_NAME, null, contentValues)
    }

    @SuppressLint("Recycle")
    override fun listGroup(coursename: String): List<GroupData> {
        val list = ArrayList<GroupData>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val course = cursor.getString(2)
                if (course == coursename) {
                    val id = cursor.getInt(0)
                    val name = cursor.getString(1)
                    val image = cursor.getInt(3)

                    val courseData = GroupData(id, name, course, image)
                    list.add(courseData)
                }
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun editGroup(groupData: GroupData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GURUH_NAME, groupData.name)
        contentValues.put(COURSE_NAME, groupData.coursename)
        contentValues.put(GURUH_IMAGE, groupData.imageRes)
        database.update(
            TABLE_NAME,
            contentValues,
            "$GURUH_ID=?",
            arrayOf(groupData.id.toString())
        )
        return
    }

    override fun deleteGroup(groupData: GroupData) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$GURUH_ID=?", arrayOf(groupData.id.toString()))
    }

    override fun getGroupCount(): Int {
        TODO("Not yet implemented")
    }




    fun Editting(oldname: String, newname: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COURSE_NAME,newname)
        database.update(
            TABLE_NAME,
            contentValues,
            "$COURSE_NAME=?",
            arrayOf(oldname)
        )
    }

    fun Editting2(oldname: String, newname: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GURUH_NAME,newname)
        database.update(
            TABLE_NAME,
            contentValues,
            "$GURUH_NAME=?",
            arrayOf(oldname)
        )
    }

    //   override fun getCourseById(id: Int): CourseData {
    //  }
}