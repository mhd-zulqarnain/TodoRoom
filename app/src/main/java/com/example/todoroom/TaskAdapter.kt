package com.example.todoroom

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class TaskAdapter(var ctx: Context, private val onClick: (TaskEntry) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {
    var list: ArrayList<TaskEntry> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val v = LayoutInflater.from(ctx).inflate(R.layout.single_task_row, p0, false)
        return MyViewHolder(v);

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getTasks(): ArrayList<TaskEntry> {
        return list
    }


    fun setTasks(taskEntries: ArrayList<TaskEntry>) {
        list = taskEntries
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onClick(list[position])
        }
        try {
            val obj = list[position]
            holder.taskDescription.setText(obj.description)
            holder.taskUpdatedAt.setText(obj.updatedAt.toString())
            val priorityCircle = holder.priorityView.getBackground() as GradientDrawable
            // Get the appropriate background color based on the priority
            val priorityColor = getPriorityColor(obj.priority)
            priorityCircle.setColor(priorityColor)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        onClick(list[position])
    }

    private fun getPriorityColor(priority: Int): Int {

        var priortyColor = 0;
        when (priority) {
            1 ->
                priortyColor = ContextCompat.getColor(ctx, R.color.materialRed)
            2 ->
                priortyColor = ContextCompat.getColor(ctx, R.color.materialOrange)
            3 ->
                priortyColor = ContextCompat.getColor(ctx, R.color.materialYellow)
        }
        return priortyColor
    }

    fun setTask(dataList: ArrayList<TaskEntry>) {
        list = dataList
        notifyDataSetChanged();
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var taskDescription: TextView
        internal var taskUpdatedAt: TextView
        internal var priorityView: TextView

        init {
            taskDescription = itemView.findViewById<View>(R.id.taskDescription) as TextView
            taskUpdatedAt = itemView.findViewById<View>(R.id.taskUpdatedAt) as TextView
            priorityView = itemView.findViewById<View>(R.id.priorityView) as TextView

        }


    }

}