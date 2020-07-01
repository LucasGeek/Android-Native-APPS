package com.example.taskschedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskschedule.R
import com.example.taskschedule.entities.TaskEntity
import com.example.taskschedule.listeners.OnTaskListFragmentInteractionListener
import com.example.taskschedule.viewholder.TaskViewHolder

class TaskListAdapter(private val taskList: List<TaskEntity>, val listener: OnTaskListFragmentInteractionListener) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent.context
        val inflator = LayoutInflater.from(context)
        val view = inflator.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view, listener, context)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindData(task)
    }
}