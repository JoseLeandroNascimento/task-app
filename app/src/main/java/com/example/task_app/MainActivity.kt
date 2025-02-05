package com.example.task_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnNewTask: Button
    private lateinit var btnMyTasks: Button
    private lateinit var tasks: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnNewTask = findViewById(R.id.btn_new_task)
        btnMyTasks = findViewById(R.id.btn_my_tasks)

        val novaTask = intent.getStringExtra("NEW_TASK")

        tasks = SharedPreferenceTasks.getTasks(this)

        novaTask?.let {
            val newTask = Task(
                id = tasks.size + 1,
                title = it
            )
            tasks.add(newTask)
            SharedPreferenceTasks.saveData(this,tasks)
        }

        btnNewTask.setOnClickListener {
            val intent = Intent(this, Activitynewtask::class.java)
            startActivity(intent)
        }

        btnMyTasks.setOnClickListener {
            val intent = Intent(this, ActivityMyTasks::class.java)
            startActivity(intent)
        }

    }


}