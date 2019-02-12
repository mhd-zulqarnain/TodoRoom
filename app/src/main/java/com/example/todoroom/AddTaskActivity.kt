package com.example.todoroom

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*


class AddTaskActivity : AppCompatActivity() {

    companion object {
        val EXTRA_TASK_ID = "extraTaskId"
        val INSTANCE_TASK_ID = "instanceTaskId"
        val PRIORITY_HIGH = 1
        val PRIORITY_MEDIUM = 2
        val PRIORITY_LOW = 3
        private val DEFAULT_TASK_ID = -1
    }

    private var mTaskId = DEFAULT_TASK_ID

    private var mDb: AppDatabase? = null

    val priorityFromViews: Int

        get() {
            var priority = 1
            val checkedId = radioGroup.checkedRadioButtonId
            when (checkedId) {
                R.id.radButton1 -> priority = PRIORITY_HIGH
                R.id.radButton2 -> priority = PRIORITY_MEDIUM
                R.id.radButton3 -> priority = PRIORITY_LOW
            }
            return priority
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        initViews()

        mDb = AppDatabase.getInstance(applicationContext)

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID)
        }

        val intent = intent
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            saveButton.setText(R.string.update_button)
            if (mTaskId == DEFAULT_TASK_ID) {
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId)
        super.onSaveInstanceState(outState)
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private fun initViews() {


        saveButton.setOnClickListener {
                onSaveButtonClicked()
        }
    }


    private fun populateUI(task: TaskEntry) {

    }


    fun onSaveButtonClicked() {
        val description = editTextTaskDescription.text.toString()
        val priority = priorityFromViews
        val date = Date()

        val taskEntry = TaskEntry(description, priority, date)
        mDb!!.taskDao().insertTast(taskEntry);
        finish()
    }


    fun setPriorityInViews(priority: Int) {
        when (priority) {
            PRIORITY_HIGH -> radioGroup.check(R.id.radButton1)
            PRIORITY_MEDIUM -> radioGroup.check(R.id.radButton2)
            PRIORITY_LOW -> radioGroup.check(R.id.radButton3)
        }
    }


}