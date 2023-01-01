package com.framidil.databinding.dataBindingModel

data class Employee(val id: Long, val name: String, val address: String) {
    fun f() = id.toString()
}