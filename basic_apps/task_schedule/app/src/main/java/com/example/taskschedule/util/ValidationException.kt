package com.example.taskschedule.util

import java.lang.Exception

class ValidationException(override val message: String): Exception(message) {
}