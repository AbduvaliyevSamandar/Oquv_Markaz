package com.example.oquvmarkaz

import android.content.Intent
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oquvmarkaz.adapter.CourseAdapter
import com.example.oquvmarkaz.adapter.GroupAdapter
import com.example.oquvmarkaz.database.My_DbHelper
import com.example.oquvmarkaz.database.My_DbHelper2
import com.example.oquvmarkaz.databinding.ItemAddBinding
import com.example.oquvmarkaz.databinding.ItemCourseBinding
import com.example.oquvmarkaz.databinding.ItemEditBinding
import com.example.oquvmarkaz.models.CourseData
import com.example.oquvmarkaz.models.GroupData
import kotlin.random.Random

class GroupsActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<GroupData>
    private lateinit var adapter: GroupAdapter
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var add_group: LinearLayout


    private lateinit var myDbhelper2: My_DbHelper2
    private lateinit var course:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups)

        myDbhelper2 = My_DbHelper2(this)

         course = intent.getStringExtra("GROUP").toString()
        list = ArrayList(myDbhelper2.listGroup(course))

        add_group = findViewById(R.id.add_group)
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler)

        toolbar.title = intent.getStringExtra("GROUP")

        val layoutManager = LinearLayoutManager(this)

        // loadData()
        recyclerView.layoutManager = layoutManager

        adapter = GroupAdapter(list, { coursee, position ->
            list.remove(coursee)
            myDbhelper2.deleteGroup(coursee)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, list.size)
        }, { coursee, position ->
            showEditingdialog(coursee, position)
            myDbhelper2.editGroup(coursee)
        },{ course, position->
            val intent = Intent(this@GroupsActivity, StudentsActivity::class.java)
            intent.putExtra("GROUP", list[position].name)
            startActivity(intent)
        }
        )
        add_group.setOnClickListener {
            val alerDialog = AlertDialog.Builder(this)
            val itemAddBinding = ItemAddBinding.inflate(layoutInflater)
            alerDialog.setView(itemAddBinding.root)
            val dialog = alerDialog.create()
            itemAddBinding.apply {
                adddd.text = "Add Group"
                addSave.setOnClickListener {

                    if (!nameAd.text.toString().equals("")){
                        val courseData = GroupData(name = nameAd.text.toString(), coursename = course, imageRes = R.drawable.img_1)
                        list.add(courseData)
                        myDbhelper2.addGroup(courseData)
                        dialog.dismiss()
                    }
                    else{
                        Toast.makeText(this@GroupsActivity,"Kurs nomini kiriting", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dialog.show()
        }
        recyclerView.adapter = adapter
    }

//    private fun loadData() {
//
//
//        val image=intent.getIntExtra("image",-1)
//        val name=intent.getStringExtra("GROUP")
//        for (i in 0..10) {
//             list.add(GroupData(imageRes=image,name = name+" "+(1+i)),c)
//        }
//    }

    private fun showEditingdialog(groupData: GroupData, position: Int) {
        val alerDialog = AlertDialog.Builder(this)
        val itemEditBinding = ItemEditBinding.inflate(layoutInflater)
        alerDialog.setView(itemEditBinding.root)
        val dialog = alerDialog.create()
        itemEditBinding.apply {
            nameEt.setText(groupData.name)

            editSave.setOnClickListener {
                val newname = nameEt.text.toString()
                groupData.name = newname
                groupData.coursename=course
                val oldname = groupData.name
                myDbhelper2.editGroup(groupData)
                myDbhelper2.Editting2(oldname, newname)
                adapter.notifyItemChanged(position)
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}