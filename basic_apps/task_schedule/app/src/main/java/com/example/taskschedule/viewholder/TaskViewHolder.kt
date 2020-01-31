package com.example.taskschedule.viewholder

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskschedule.R
import com.example.taskschedule.entities.TaskEntity
import com.example.taskschedule.listeners.OnTaskListFragmentInteractionListener
import com.example.taskschedule.repository.PriorityCacheConstants

class TaskViewHolder(
    itemView: View,
    private val listener: OnTaskListFragmentInteractionListener,
    private val context: Context
) : RecyclerView.ViewHolder(itemView) {

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDuoDate: TextView = itemView.findViewById(R.id.textDuoDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)

    fun bindData(task: TaskEntity) {
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityId)
        mTextDuoDate.text = task.dueDate

        if (task.complete) {
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        mTextDescription.setOnClickListener {
            listener.onListClick(task.id)
        }

        mTextDescription.setOnLongClickListener {
            showConfimationDialog(task)
            true
        }

        mImageTask.setOnClickListener {
            if (task.complete) {
                listener.onUncomplete(task.id)
            } else {
                listener.onComplete(task.id)
            }
        }
    }

    private fun showConfimationDialog(task: TaskEntity) {
        AlertDialog.Builder(context)
            .setTitle("Remoção de tarefa")
            .setMessage("Deseja remover '${task.description}'?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Remover", handleRemove(listener, taskId = task.id))
            .setNegativeButton("Cancelar", null).show()

    }

    private class handleRemove(
        val listener: OnTaskListFragmentInteractionListener,
        val taskId: Int
    ) : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            listener.onDeleteClick(taskId)
        }

    }
}