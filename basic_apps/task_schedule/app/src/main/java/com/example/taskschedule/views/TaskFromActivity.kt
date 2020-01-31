package com.example.taskschedule.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskschedule.R
import com.example.taskschedule.business.PriorityBusiness
import com.example.taskschedule.business.TaskBusiness
import com.example.taskschedule.constants.TaskConstants
import com.example.taskschedule.entities.PriorityEntity
import kotlinx.android.synthetic.main.activity_task_from.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class TaskFromActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mTaskBusiness: TaskBusiness
    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    private var mLstPriorityEntity: MutableList<PriorityEntity> = mutableListOf()
    private var mLstPriorityId: MutableList<Int> = mutableListOf()
    private var mTaskId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_from)

        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)

        loadPriorities()
        setListeners()
        loadDataFromActivity()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonDate -> {
                openDatePickeDialog()
            }
            R.id.buttonSave -> {
                handleCreate()
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        buttonDate.text = mSimpleDateFormat.format(calendar.time)
    }

    private fun loadDataFromActivity() {
        //Se estiver diferente de null ele vai retornar um ID com valor

        val bundle = intent.extras

        if (bundle != null) {
            mTaskId = bundle.getInt(TaskConstants.BUNDLE.TASK_ID)
            val task = mTaskBusiness.get(mTaskId)

            if(task != null) {
                editDescription.setText(task.description)
                buttonDate.text = task.dueDate
                checkComplete.isChecked = task.complete
                spinnerPriority.setSelection(getIndex(task.priorityId))

                buttonSave.text = getString(R.string.task_create_update)
            }
        }
    }

    private fun handleCreate() {
        try {
            val description = editDescription.text.toString()
            val priorityid = mLstPriorityId[spinnerPriority.selectedItemPosition]
            val complete = checkComplete.isChecked
            val duoDate = buttonDate.text.toString()

            if(mTaskId == 0) {
                mTaskBusiness.insert(priorityid, complete, duoDate, description)
                Toast.makeText(this, getString(R.string.tarefa_incluida_com_sucesso), Toast.LENGTH_LONG).show()
            } else {
                mTaskBusiness.update(mTaskId, priorityid, complete, duoDate, description)
                Toast.makeText(this, getString(R.string.tarefa_alterada_com_sucesso), Toast.LENGTH_LONG).show()
            }

            finish()

        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }
    }

    private fun openDatePickeDialog() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOnMonth = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, dayOnMonth).show()
    }

    private fun setListeners() {
        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun getIndex(id: Int): Int {
        var index = 0
        for (i in 0..mLstPriorityEntity.size) {
            if(mLstPriorityEntity[i].id == id) {
                index = i
                break
            }
        }

        return index
    }

    private fun loadPriorities() {
        mLstPriorityEntity = mPriorityBusiness.getList()

        val lstPriorities = mLstPriorityEntity.map { it.description }
        mLstPriorityId = mLstPriorityEntity.map { it.id }.toMutableList()

        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lstPriorities)
        spinnerPriority.adapter = adapter
    }
}