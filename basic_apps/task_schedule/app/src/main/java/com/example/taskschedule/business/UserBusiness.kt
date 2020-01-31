package com.example.taskschedule.business

import android.content.Context
import com.example.taskschedule.R
import com.example.taskschedule.constants.TaskConstants
import com.example.taskschedule.entities.UserEntity
import com.example.taskschedule.repository.UserRepository
import com.example.taskschedule.util.SecurityPreferences
import com.example.taskschedule.util.ValidationException
import java.lang.Exception

class UserBusiness(private val context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login(email: String, password: String): Boolean {
        val user: UserEntity? = mUserRepository.get(email, password)

        return if (user != null) {
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)

            true
        } else {
            false
        }
    }

    fun Insert(name: String, email: String, password: String) {

        try {
            if (name.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
                throw ValidationException(context.getString(R.string.informe_todos_os_campos))
            }

            if (mUserRepository.isEmailExistent(email)) {
                throw ValidationException(context.getString(R.string.email_em_uso))
            }

            val userId = mUserRepository.Insert(name, email, password)

            if (userId != -1) {
                mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
                mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
                mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)
            } else {
                throw ValidationException(context.getString(R.string.erro_ao_cadastrar_usuario))
            }

        } catch (excep: Exception) {
            throw excep
        }
    }

    fun verifyLogged(): Boolean {
        val userName = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)
        val userEmail = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)

        return !(userName.isNullOrEmpty() || userEmail.isNullOrEmpty() || userId.isNullOrEmpty())
    }

    fun doLogout() {
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_NAME)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_EMAIL)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_ID)
    }

    fun getNameUser(): String {
        return mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)
    }

    fun getEmailUser(): String {
        return mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)
    }
}