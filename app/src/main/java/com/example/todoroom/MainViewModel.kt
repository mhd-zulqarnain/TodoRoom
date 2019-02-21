package com.example.todoroom

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import android.app.Application


/*
class MainViewModel(application:Application) : AndroidViewModel(application) {

}*/

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val tasks: LiveData<List<TaskEntry>>
    init {
        val database = AppDatabase.getInstance(this.getApplication())
        tasks = database.taskDao().loadAllEntry()
    }

}