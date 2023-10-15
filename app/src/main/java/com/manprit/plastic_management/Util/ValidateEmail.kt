package com.manprit.plastic_management.Util

fun String.isValidEmail() : Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()