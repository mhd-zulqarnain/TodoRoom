package com.example.todoroom

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class AddTaskViewModel(database:AppDatabase , taskId:Int ) : ViewModel() {
    val tasks: LiveData<TaskEntry>

    init {
        tasks = database.taskDao().loadTaskByid(taskId)
    }
}