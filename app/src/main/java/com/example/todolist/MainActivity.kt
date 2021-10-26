package com.example.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.LogRecord

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.excluir -> {
                val position = todoAdapter.getCurrentPosition();

                todoAdapter.deleteOne(position);
            }
            R.id.cancelar -> {
                Log.d("menu", "cancelado")
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf())

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.length < 3){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Necessário no mínimo 3 caracteres")
                builder.setPositiveButton("OK, entendi!",{ dialogInterface: DialogInterface?, i: Int -> return@setPositiveButton})
                builder.show()
            }
            if (todoTitle.length >= 3) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }
        }

        btnDeleteDoneTodos.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Finalizar/Excluir!")
            builder.setMessage("Atenção, voce está prestes á finalizar/excluir uma tarefa diaria. Deseja continuar?")
            builder.setPositiveButton("SIM") { dialog, _ -> todoAdapter.deleteDoneTodos() }
            builder.setNegativeButton("NAO") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }



        }
    }



