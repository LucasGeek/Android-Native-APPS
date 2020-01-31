package com.example.taskschedule.repository
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.taskschedule.constants.DataBaseConstants

class TaskDataBaseHelper (context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION: Int = 2
        private val DATABASE_NAME: String = "TASK_SCHEDULE.db"
    }

    private val createTableUser = """ CREATE TABLE ${DataBaseConstants.USER.TABLE_NAME} (
         ${DataBaseConstants.USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
         ${DataBaseConstants.USER.COLUMNS.NAME} TEXT,
         ${DataBaseConstants.USER.COLUMNS.EMAIL} TEXT,
         ${DataBaseConstants.USER.COLUMNS.PASSWORD} TEXT
         );""".trimMargin()

    private val createTablePriority = """ CREATE TABLE ${DataBaseConstants.PRIORITY.TABLE_NAME} (
         ${DataBaseConstants.PRIORITY.COLUMNS.ID} INTEGER PRIMARY KEY,
         ${DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION} TEXT
         );""".trimMargin()

    private val createTableTask = """ CREATE TABLE ${DataBaseConstants.TASK.TABLE_NAME} (
         ${DataBaseConstants.TASK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
         ${DataBaseConstants.TASK.COLUMNS.USER_ID} INTEGER,
         ${DataBaseConstants.TASK.COLUMNS.PRIORITY_ID} INTEGER,
         ${DataBaseConstants.TASK.COLUMNS.DESCRIPTION} TEXT,
         ${DataBaseConstants.TASK.COLUMNS.COMPLETE} INTEGER,
         ${DataBaseConstants.TASK.COLUMNS.DUEDATE} TEXT
         );""".trimMargin()

    private val insertPriorities =
        """INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME} VALUES (1, 'Baixa'), (2, 'Média'), (3, 'Alta'), (4, 'Crítica')""".trimMargin()

    private val deleteTableUser = "DROP TABLE IF EXISTS ${DataBaseConstants.USER.TABLE_NAME};"
    private val deleteTablePriority = "DROP TABLE IF EXISTS ${DataBaseConstants.PRIORITY.TABLE_NAME};"
    private val deleteTableTask = "DROP TABLE IF EXISTS ${DataBaseConstants.TASK.TABLE_NAME};"

    override fun onCreate(db: SQLiteDatabase) {
        createDataTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        deleteDataTable(db)
        createDataTable(db)
    }

    private fun createDataTable(db: SQLiteDatabase) {
        db.execSQL(createTableUser)
        db.execSQL(createTablePriority)
        db.execSQL(insertPriorities)
        db.execSQL(createTableTask)
    }

    private fun deleteDataTable(db: SQLiteDatabase) {
        db.execSQL(deleteTableUser)
        db.execSQL(deleteTablePriority)
        db.execSQL(deleteTableTask)
    }
}