package com.example.todoroom

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class AddTaskViewModelFactory(var mDb: AppDatabase,var mTaskId: Int) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return AddTaskViewModel(mDb, mTaskId) as T
    }
}