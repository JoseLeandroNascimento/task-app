package com.example.task_app

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ActivityMyTasks : AppCompatActivity() {

    private lateinit var listTask: RecyclerView
    private lateinit var itemsTasks: MutableList<Task>
    private lateinit var emptyView: TextView
    private lateinit var btnVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_tasks)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        itemsTasks = SharedPreferenceTasks.getTasks(this)
        listTask = findViewById(R.id.list_tasks)
        emptyView = findViewById(R.id.empty_view)
        btnVoltar = findViewById(R.id.btn_voltar)

        verifyListEmpty()

        val adapter = MyTasksAdapter(itemsTasks, this) { id ->

//            AlertDialog.Builder(this)
//                .setTitle("Deletar tarefa")
//                .setMessage("Gostaria de excluir a tarefa")
//                .setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, i: Int ->
//                    itemsTasks = itemsTasks.filter {
//                        it.id != id
//                    }.toMutableList()
//                    SharedPreferenceTasks.saveData(this@ActivityMyTasks, itemsTasks)
//                    verifyListEmpty()
//                }.show()


        }

        btnVoltar.setOnClickListener {
            finish()
        }

        listTask.adapter = adapter
        listTask.layoutManager = LinearLayoutManager(this)


    }

    private fun verifyListEmpty() {
        if (itemsTasks.isEmpty()) {
            listTask.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            listTask.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }

    inner class MyTasksAdapter(
        private val items: MutableList<Task>,
        private val context: Context,
        private val onDeleteClick: (id: Int) -> Unit
    ) : RecyclerView.Adapter<MyTaskHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskHolder {

            val view = layoutInflater.inflate(
                R.layout.task_item,
                parent,
                false
            )

            return MyTaskHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: MyTaskHolder, position: Int) {

            val task = items[position]
            holder.bind(task)

            holder.btnDelete.setOnClickListener {

                AlertDialog.Builder(context)
                    .setTitle(R.string.dialog_title_delete_task)
                    .setMessage(R.string.dialog_message_delete_task)
                    .setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                        itemsTasks = itemsTasks.filter {
                            it.id != 1
                        }.toMutableList()
                        SharedPreferenceTasks.saveData(this@ActivityMyTasks, itemsTasks)
                        items.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, items.size)
                        verifyListEmpty()

                    }.show()

            }

        }

    }

    class MyTaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var task: Task
        lateinit var btnDelete: ImageView

        fun bind(item: Task) {

            task = item

            val titleTask = itemView.findViewById<TextView>(R.id.title_task_item)
            titleTask.text = task.title
            btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete)

        }

    }

}
