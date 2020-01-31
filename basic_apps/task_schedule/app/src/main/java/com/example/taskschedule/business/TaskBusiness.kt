package com.example.taskschedule.business

import android.content.Context
import com.example.taskschedule.constants.TaskConstants
import com.example.taskschedule.entities.TaskEntity
import com.example.taskschedule.repository.TaskRepository
import com.example.taskschedule.util.SecurityPreferences

class TaskBusiness(context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun getList(taskFilter: Int): MutableList<TaskEntity> = mTaskRepository.getList(getUserId(), taskFilter)

    fun insert(priorityId: Int, complete: Boolean, duoDate: String, description: String) {
        val userId: Int = getUserId()
        val taskEntity = TaskEntity(0, userId, priorityId, description, complete, duoDate)

        mTaskRepository.insert(taskEntity)
    }

    fun update(taskId: Int, priorityId: Int, complete: Boolean, duoDate: String, description: String) {
        val userId: Int = getUserId()
        val taskEntity = TaskEntity(taskId, userId, priorityId, description, complete, duoDate)

        mTaskRepository.update(taskEntity)
    }

    fun update(taskEntity: TaskEntity) = mTaskRepository.update(taskEntity)

    fun getUserId(): Int = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()

    fun get(taskId: Int) = mTaskRepository.get(taskId)

    fun delete(taskId: Int) = mTaskRepository.remove(taskId)

    fun complete(id: Int, complete: Boolean) {
        var task = mTaskRepository.get(id)

        if(task != null) {
            task.complete = complete
            update(task)
        }
    }
}