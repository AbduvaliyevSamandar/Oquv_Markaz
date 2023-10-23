package com.example.oquvmarkaz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oquvmarkaz.adapter.CourseAdapter
import com.example.oquvmarkaz.database.My_DbHelper
import com.example.oquvmarkaz.database.My_DbHelper2
import com.example.oquvmarkaz.databinding.ItemAddBinding
import com.example.oquvmarkaz.databinding.ItemEditBinding
import com.example.oquvmarkaz.models.CourseData
import com.example.oquvmarkaz.models.GroupData
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<CourseData>
    private lateinit var adapter:CourseAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var add_course: LinearLayout

    lateinit var myDbhelper2: My_DbHelper2

    lateinit var myDbhelper: My_DbHelper

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDbhelper = My_DbHelper(this)
        myDbhelper2 = My_DbHelper2(this)
        list = ArrayList(myDbhelper.listCourse())
        add_course = findViewById(R.id.add_contact)
        recyclerView = findViewById(R.id.recycler)
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        adapter=CourseAdapter(list,{coursedata,position->
            list.remove(coursedata)
            myDbhelper.deleteCourse(coursedata)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemChanged(position, list.size)
        },{coursedata,position->
            myDbhelper.editCourse(coursedata)
          showEditingdialog(coursedata,position)

        },{ course, position->
            val intent = Intent(this@MainActivity, GroupsActivity::class.java)
            intent.putExtra("GROUP", list[position].name)
            intent.putExtra("image", list[position].ImageRes)
            startActivity(intent)
        })
        add_course.setOnClickListener {
            val alerDialog = AlertDialog.Builder(this)
            val itemAddBinding = ItemAddBinding.inflate(layoutInflater)
            alerDialog.setView(itemAddBinding.root)
            val dialog = alerDialog.create()
            itemAddBinding.apply {
                addSave.setOnClickListener {
                    val rnd = Random
                    val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                    if(!nameAd.text.toString().equals("")){
                        val course=CourseData(
                            ImageRes = R.drawable.android,
                            name = nameAd.text.toString()
                        )
                        list.add(course)
                        myDbhelper.addCourse(course)
                        dialog.dismiss()
                    }
                    else {
                        Toast.makeText(this@MainActivity,"Kurs nomini kiriting",Toast.LENGTH_SHORT).show()
                    }

                }
            }
            dialog.show()
        }
        recyclerView.adapter = adapter
    }

    private fun showEditingdialog(course: CourseData, position: Int) {
        val alerDialog = AlertDialog.Builder(this)

        val itemEditBinding = ItemEditBinding.inflate(layoutInflater)

        alerDialog.setView(itemEditBinding.root)

        val dialog = alerDialog.create()

        itemEditBinding.apply {
            nameEt.setText(course.name)
            editSave.setOnClickListener {
                val newname = nameEt.text.toString()
                val oldname = course.name
                course.name = newname
                myDbhelper.editCourse(course)
                myDbhelper2.Editting(oldname, newname)
                adapter.notifyItemChanged(position)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

}
