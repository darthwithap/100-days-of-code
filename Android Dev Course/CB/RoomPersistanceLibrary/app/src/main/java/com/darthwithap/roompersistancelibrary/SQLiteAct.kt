package com.darthwithap.roompersistancelibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.darthwithap.roompersistancelibrary.db.MyDBHelper
import com.darthwithap.roompersistancelibrary.db.TodoTable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_s_q_lite.*
import kotlinx.android.synthetic.main.list_item_todo.view.*

class SQLiteAct : AppCompatActivity() {
    val todos = ArrayList<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_q_lite)

        val todoAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, todos)

        val db = MyDBHelper(this).writableDatabase

        fun refreshTodoList() {
            val todoList = TodoTable.getAllTodos(db)
            todos.clear()
            todos.addAll(todoList)
            todoAdapter.notifyDataSetChanged()
        }

        lvTodos.adapter = todoAdapter

        btnAddTodo.setOnClickListener {
            val newTodo = Todo(etNewTodo.text.toString(), false)
            TodoTable.insertTodo(db, newTodo)
            refreshTodoList()
            todos.add(newTodo)
            todoAdapter.notifyDataSetChanged()
        }

        refreshTodoList()
    }

    inner class TodoAdapter() : BaseAdapter() {
        override fun getCount() = todos.size

        override fun getItem(position: Int) = todos[position]

        override fun getItemId(position: Int) = 0L

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val itemView = layoutInflater.inflate(R.layout.list_item_todo, parent, false)
            itemView.tvTask.text = getItem(position).task
            itemView.tvDone.text = getItem(position).done.toString()
            return itemView
        }
    }
    
}