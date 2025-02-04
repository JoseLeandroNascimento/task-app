package com.example.task_app

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceTasks {

    companion object{

        fun  saveData(context: Context, data: MutableList<Task>) {
            val gson = Gson()
            val jsonString = gson.toJson(data)
            val sharedPreferences = context.getSharedPreferences("APP_DATA", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString("MY_TASKS", jsonString)
                apply()
            }
        }

        fun getTasks(context: Context): MutableList<Task> {
            val sharedPreferences = context.getSharedPreferences("APP_DATA", Context.MODE_PRIVATE)
            val json = sharedPreferences.getString("MY_TASKS", null)

            var tasks: MutableList<Task> = mutableListOf()

            json?.let {
                val gson = Gson()
                val type = object : TypeToken<List<Task>>() {}.type
                tasks = gson.fromJson(json, type)
            }

            return tasks
        }

    }
}