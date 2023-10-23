package com.example.oquvmarkaz.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oquvmarkaz.databinding.ActivityGroupsBinding
import com.example.oquvmarkaz.databinding.ItemCourseBinding
import com.example.oquvmarkaz.models.CourseData
import com.example.oquvmarkaz.models.GroupData
import kotlin.random.Random

class GroupAdapter(val list: List<GroupData>,
                   val onItemDelete: (GroupData, Int) -> Unit,
                   val onItemEdit: (GroupData, Int) -> Unit,
                   val onClick: (GroupData, Int) -> Unit,
    ):RecyclerView.Adapter<GroupAdapter.Vh>(){

    inner class Vh(var itemCourseBinding: ItemCourseBinding):RecyclerView.ViewHolder(itemCourseBinding.root){
        fun onBind(groupdata: GroupData, position: Int) {
            itemCourseBinding.apply {
                courseName.text = groupdata.name

                val a=courseName.text.substring(0,1)
                text2.text=a

                delete.setOnClickListener { onItemDelete.invoke(groupdata, position) }
                edit.setOnClickListener { onItemEdit.invoke(groupdata, position) }
                layout.setOnClickListener { onClick.invoke(groupdata, position) }

                poapmenu.setOnClickListener{
                    expadle.isExpanded = !expadle.isExpanded
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCourseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }
}