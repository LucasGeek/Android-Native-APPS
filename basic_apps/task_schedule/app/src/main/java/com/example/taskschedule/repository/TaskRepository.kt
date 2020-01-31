package com.example.taskschedule.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.taskschedule.constants.DataBaseConstants
import com.example.taskschedule.entities.TaskEntity
import java.lang.Exception

class TaskRepository private constructor(context: Context) {
    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }

            return INSTANCE as TaskRepository
        }

        private var INSTANCE: TaskRepository? = null
    }

    fun insert(task: TaskEntity): Int  {
        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val complete: Int = if(task.complete) {
                1
            } else {
                0
            }

            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.TASK.COLUMNS.USER_ID, task.userId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITY_ID, task.priorityId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)

            return db.insert(DataBaseConstants.TASK.TABLE_NAME, null, insertValues).toInt()
        } catch (e: Exception) {
            throw e
        }
    }

    fun update(task: TaskEntity) {
        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val complete: Int = if(task.complete) {
                1
            } else {
                0
            }

            val updateValues = ContentValues()
            updateValues.put(DataBaseConstants.TASK.COLUMNS.USER_ID, task.userId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITY_ID, task.priorityId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)

            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(task.id.toString())

            db.update(DataBaseConstants.TASK.TABLE_NAME, updateValues, selection, selectionArgs)
        } catch (e: Exception) {
            throw e
        }
    }

    fun remove(id: Int) {
        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val where = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(id.toString())

            db.delete(DataBaseConstants.TASK.TABLE_NAME, where, whereArgs)
        } catch (e: Exception) {
            throw e
        }
    }

    fun get(id: Int): TaskEntity? {
        var taskEntity: TaskEntity? = null

        try {
            val db = mTaskDataBaseHelper.readableDatabase
            val cursor: Cursor

            val projection = arrayOf(DataBaseConstants.TASK.COLUMNS.ID,
                DataBaseConstants.TASK.COLUMNS.USER_ID,
                DataBaseConstants.TASK.COLUMNS.PRIORITY_ID,
                DataBaseConstants.TASK.COLUMNS.DESCRIPTION,
                DataBaseConstants.TASK.COLUMNS.DUEDATE,
                DataBaseConstants.TASK.COLUMNS.COMPLETE)

            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val selecttionArgs = arrayOf(id.toString())

            cursor = db.query(DataBaseConstants.TASK.TABLE_NAME, projection, selection, selecttionArgs, null, null, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()

                val taskId =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                val taskUserId =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USER_ID))
                val taskPriorityId =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITY_ID))
                val taskDescription =  cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                val taskDuoDate =  cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                val taskComplete =  (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)

                taskEntity = TaskEntity(taskId, taskUserId, taskPriorityId, taskDescription, taskComplete, taskDuoDate)
            }

            cursor.close()
        } catch (e: Exception) {
            return taskEntity
        }

        return taskEntity
    }

    fun getList(userId: Int, taskFilter: Int): MutableList<TaskEntity> {
        var list = mutableListOf<TaskEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME} WHERE ${DataBaseConstants.TASK.COLUMNS.USER_ID} = $userId AND ${DataBaseConstants.TASK.COLUMNS.COMPLETE} = $taskFilter", null)
            if(cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val taskId =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val taskUserId =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USER_ID))
                    val taskPriorityId =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITY_ID))
                    val taskDescription =  cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                    val taskDuoDate =  cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                    val taskComplete =  (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)

                    list.add(TaskEntity(taskId, taskUserId, taskPriorityId, taskDescription, taskComplete, taskDuoDate))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }
}