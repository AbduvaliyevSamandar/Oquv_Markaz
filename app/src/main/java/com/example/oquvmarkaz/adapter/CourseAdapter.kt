package com.example.oquvmarkaz.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.oquvmarkaz.R
import com.example.oquvmarkaz.databinding.ItemCourseBinding
import com.example.oquvmarkaz.models.CourseData
import kotlin.random.Random


class CourseAdapter(
    val list: List<CourseData>,
    val onItemDelete: (CourseData, Int) -> Unit,
    val onItemEdit: (CourseData, Int) -> Unit,
    val onItemClick: (CourseData,Int) -> Unit,
)
    : RecyclerView.Adapter<CourseAdapter.Vh>() {




    inner class Vh(var itemCourseBinding: ItemCourseBinding) :
        RecyclerView.ViewHolder(itemCourseBinding.root) {

        fun onBind(courseData: CourseData, position: Int) {
            itemCourseBinding.apply {
                courseName.text = courseData.name

                val rnd = Random
                val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

                val a: String =courseName.text.substring(0,1)
                text2.text=a
                delete.setOnClickListener { onItemDelete.invoke(courseData, position) }
                edit.setOnClickListener { onItemEdit.invoke(courseData, position) }
                itemView.setOnClickListener { onItemClick.invoke(courseData,position) }

                poapmenu.setOnClickListener{
                    expadle.isExpanded = !expadle.isExpanded
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }
}




