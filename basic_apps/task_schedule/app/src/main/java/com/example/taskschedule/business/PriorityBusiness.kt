package com.example.taskschedule.business

import android.content.Context
import com.example.taskschedule.entities.PriorityEntity
import com.example.taskschedule.repository.PriorityRepository

class PriorityBusiness (private val context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()
}