 package com.darthwithap.roompersistancelibrary

data class Todo(var task: String, var done: Boolean) {
    override fun toString(): String {
        return task
    }
}