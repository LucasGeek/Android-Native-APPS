package com.example.taskschedule.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskschedule.R
import com.example.taskschedule.adapter.TaskListAdapter
import com.example.taskschedule.business.TaskBusiness
import com.example.taskschedule.constants.TaskConstants
import com.example.taskschedule.listeners.OnTaskListFragmentInteractionListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mListener: OnTaskListFragmentInteractionListener
    private var mTaskFilter: Int = 0

    companion object {
        fun newInstance(taskFilter: Int): TaskListFragment {
            val args: Bundle = Bundle()
            args.putInt(TaskConstants.TASK_FILTER.KEY, taskFilter)

            val fragment = TaskListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTaskFilter = arguments!!.getInt(TaskConstants.TASK_FILTER.KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)
        mContext = rootView.context
        mTaskBusiness = TaskBusiness(mContext)
        mListener = object : OnTaskListFragmentInteractionListener {
            override fun onListClick(taskId: Int) {

                val bundle: Bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASK_ID, taskId)

                val intent = Intent(mContext, TaskFromActivity::class.java)
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDeleteClick(id: Int): Boolean {
                mTaskBusiness.delete(id)
                loadTasks()

                Toast.makeText(mContext, getString(R.string.tarefa_removida_com_sucesso), Toast.LENGTH_LONG).show()

                return true
            }

            override fun onUncomplete(id: Int) {
                mTaskBusiness.complete(id, false)
                loadTasks()
            }

            override fun onComplete(id: Int) {
                mTaskBusiness.complete(id, true)
                loadTasks()
            }
        }

        // 1 Obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recycleTaskList)

        // 2 Definir um adapter com os itens de listagem
        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf(), mListener)

        // 3 Definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)

        return rootView
    }

    override fun onResume() {
        super.onResume()

        loadTasks()
    }

    private fun loadTasks() {
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList(mTaskFilter), mListener)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.floatAddTask -> {
                startActivity(Intent(mContext, TaskFromActivity::class.java))
            }
        }
    }
}