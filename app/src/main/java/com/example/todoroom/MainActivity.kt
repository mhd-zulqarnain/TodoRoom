package com.example.todoroom


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var mDb: AppDatabase
    private var mAdapter: TaskAdapter? = null
    val EXTRA_TASK_ID = "extraTaskId"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mDb = AppDatabase.getInstance(this)
        mAdapter = TaskAdapter(this@MainActivity){
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.EXTRA_TASK_ID,it.id)
            startActivity(intent)

        }
        mRecyclerView.setLayoutManager(LinearLayoutManager(this));
        mRecyclerView.adapter = mAdapter
        fab.setOnClickListener { view ->

            startActivity(Intent(this@MainActivity, AddTaskActivity::class.java))
        }
        val decoration = DividerItemDecoration(applicationContext, VERTICAL)
        mRecyclerView.addItemDecoration(decoration)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                AppExecutors.getInstance().diskIO.execute(object :Runnable{
                    override fun run() {
                        var position = viewHolder.adapterPosition
                        var obj = mAdapter!!.getTasks().get(position)
                        mDb.taskDao().deleteTask(obj)
                        retreiveData()
                    }

                })


            }
        }).attachToRecyclerView(mRecyclerView)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        retreiveData()
    }

    private fun retreiveData() {
        AppExecutors.getInstance().diskIO.execute(object : Runnable {
            val list = mDb.taskDao().loadAllEntry()
            override fun run() {
                runOnUiThread{
                    mAdapter!!.setTasks( list as ArrayList);
                }
            }
        })
    }

}
