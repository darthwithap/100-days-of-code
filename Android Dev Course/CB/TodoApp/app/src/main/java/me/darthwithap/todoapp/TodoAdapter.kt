package me.darthwithap.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter(private val todos: List<TodoModel>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemId(position: Int) = todos[position].id

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todoModel: TodoModel) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.category_colors)
                val color = colors[TaskActivity.categories.indexOf(todoModel.category)]
                vColorTag.setBackgroundColor(color)
                tvTitle.text = todoModel.title
                tvDescription.text = todoModel.descrption
                tvCategory.text = todoModel.category
                tvDate.text = SimpleDateFormat(resources.getString(R.string.date_format)).format(
                    Date(todoModel.date)
                )
                tvTime.text = SimpleDateFormat(resources.getString(R.string.time_format)).format(
                    Date(todoModel.time)
                )
            }
        }

    }
}