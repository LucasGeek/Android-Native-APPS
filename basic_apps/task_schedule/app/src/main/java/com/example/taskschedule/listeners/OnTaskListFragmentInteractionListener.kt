package com.example.taskschedule.listeners

interface OnTaskListFragmentInteractionListener {

    fun onListClick(taskId: Int)

    fun onDeleteClick(id: Int): Boolean

    fun onUncomplete(id: Int)

    fun onComplete(id: Int)
}