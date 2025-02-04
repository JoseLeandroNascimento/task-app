package com.example.task_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_new_task : AppCompatActivity() {

    private lateinit var btnSave: Button
    private lateinit var btnBack: Button
    private lateinit var editTitle: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSave = findViewById(R.id.btn_save)
        btnBack = findViewById(R.id.btn_back)
        editTitle = findViewById(R.id.edt_titulo)

        btnBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {

            if (!isValid()) {
                Toast.makeText(this, R.string.message_form_invalid, Toast.LENGTH_LONG).show()
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NEW_TASK", editTitle.text.toString())
            startActivity(intent)

        }


    }

    private fun isValid(): Boolean {
        val value = editTitle.text
        return value.isNotEmpty() && value.isNotBlank()
    }
}