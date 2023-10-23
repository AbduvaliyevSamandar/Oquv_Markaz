package com.example.oquvmarkaz.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.oquvmarkaz.models.CourseData

@Suppress("UNREACHABLE_CODE")
class My_DbHelper(context: Context):SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
),DatabaseServer {
    companion object{
        const val DB_NAME="oquv markaz"
        const val DB_VERSION=1

        const val TABLE_NAME="kurslar"
        const val KURS_ID="id"
        const val KURS_NAME="name"
        const val KURS_IMAGE="image"
        const val KURS_Color="image"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val query=
            "create table $TABLE_NAME($KURS_ID integer not null primary key autoincrement,$KURS_NAME text not null,$KURS_IMAGE integer not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }

    override fun addCourse(courseData: CourseData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KURS_NAME,courseData.name)
        contentValues.put(KURS_IMAGE,courseData.ImageRes)

        database.insert(TABLE_NAME,null,contentValues)

    }

    override fun listCourse(): List<CourseData> {
        val list=ArrayList<CourseData>()
        val database=this.readableDatabase
        val query="select * from $TABLE_NAME"
        val cursor=database.rawQuery(query,null)
        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val image = cursor.getInt(2)

                val courseData=CourseData(id,name,image)
                list.add(courseData)

            }while (cursor.moveToNext())
        }
        return list
    }

    override fun editCourse(courseData: CourseData) {
        val database=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KURS_NAME,courseData.name)
        contentValues.put(KURS_IMAGE,courseData.ImageRes)
        database.update(
            TABLE_NAME,
            contentValues,
            "$KURS_ID=?",
            arrayOf(courseData.id.toString())
        )
    }

    override fun deleteCourse(courseData: CourseData) {
        val database=this.writableDatabase
        database.delete(TABLE_NAME,"$KURS_ID=?", arrayOf(courseData.id.toString()))
    }

    override fun getCourseCount(): Int {
        return 0
    }

 //   override fun getCourseById(id: Int): CourseData {
  //  }
}