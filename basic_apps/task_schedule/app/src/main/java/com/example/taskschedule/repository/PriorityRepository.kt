package com.example.taskschedule.repository

import android.content.Context
import android.database.Cursor
import com.example.taskschedule.constants.DataBaseConstants
import com.example.taskschedule.entities.PriorityEntity
import java.lang.Exception

class PriorityRepository private constructor(context: Context) {
    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): PriorityRepository {
            if (INSTANCE == null) {
                INSTANCE = PriorityRepository(context)
            }

            return INSTANCE as PriorityRepository
        }

        private var INSTANCE: PriorityRepository? = null
    }

    fun getList(): MutableList<PriorityEntity> {
        var list = mutableListOf<PriorityEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.PRIORITY.TABLE_NAME}", null)
            if(cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val priorityId =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMNS.ID))
                    val priorityDescription =  cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION))

                    list.add(PriorityEntity(priorityId, priorityDescription))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }
}